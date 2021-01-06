package torimia.superheroes.arena.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.ArenaMapper;
import torimia.superheroes.arena.ArenaRepository;
import torimia.superheroes.arena.BattleParticipantRepository;
import torimia.superheroes.arena.model.dto.*;
import torimia.superheroes.arena.model.entity.Arena;
import torimia.superheroes.arena.model.entity.BattleParticipant;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForBattle;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ArenaServiceMQ implements ArenaService {

    private final ArenaRepository repository;
    private final ArenaMapper mapper;
    private final SuperheroRepository superheroRepository;
    private final BattleParticipantRepository battleParticipantRepository;
    private final SuperheroMapper superheroMapper;

    @Value("${rabbitmq.queue.battle.name}")
    private String battleResultQueueName;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public MessageDto battle(BattleDto dto) {

        Superhero defaultSuperhero = superheroRepository.getOne(1L);

        Arena arena = Arena.builder()
                .loser(defaultSuperhero)
                .winner(defaultSuperhero)
                .battleTime(0L)
                .attackNumber(0)
                .date(Date.valueOf(LocalDate.now()))
                .fightStatus(FightStatus.STARTED)
                .build();
        repository.save(arena);

        fillBattleParticipant(dto.getFirstFighterId(), arena);
        fillBattleParticipant(dto.getSecondFighterId(), arena);

        Battle battle = createBattle(dto, arena.getId());

        MessageDto response;
        try {
            rabbitTemplate.convertAndSend(battleResultQueueName, battle);
            response = MessageDto.builder().message("Fight started").build();

//            throw new RuntimeException();
        } catch (Exception ex) {
            log.error("Error in sending battle request: {}, message: {}", ex, ex.getMessage());
            response = MessageDto.builder().message("Fight not started").build(); // вертати статус 500?
            arena.setFightStatus(FightStatus.NOT_STARTED);
            repository.save(arena);
        }

        return response;
    }

    @RabbitListener(queues = "fight-status")
    public void fightStatus(MessageDtoMQ message) {
        Arena arena = repository.getOne(message.getId());
        log.info("Received battle status: {}", message);
        if ((message.getMessage() != FightStatus.FINISHED_SUCCESSFUL) || (message.getMessage() != FightStatus.STARTED))
            arena.setFightStatus(message.getMessage());//  чи треба перевіряти енам, якщо приходить такий самий енам, тут може бути два статуси
        else
            arena.setFightStatus(FightStatus.FINISHED_UNSUCCESSFUL); // якщо зробити, почне пропускати тільки правильні хороші статуси
        repository.save(arena);
        log.info("Saved arena entity with status: {}", arena.toString());
    }

    @RabbitListener(queues = "battle-result")
    public void battleResult(ArenaBattleDto result) {
        log.info("Battle result: {}", result);
        saveBattleResult(result);
    }

    private Battle createBattle(BattleDto dto, Long id) {
        return Battle.builder()
                .id(id)
                .superhero1(getFighter(dto.getFirstFighterId()))
                .superhero2(getFighter(dto.getSecondFighterId()))
                .build();
    }

    private SuperheroDtoForBattle getFighter(Long id) {
        return superheroMapper.toDtoForBattle(superheroRepository.getOne(id));
    }

    @Override
    public void saveBattleResult(ArenaBattleDto dto) {
        Arena battle = repository.getOne(dto.getId());
        battle.setFightStatus(FightStatus.FINISHED_SUCCESSFUL);
        mapper.toEntityUpdate(dto, battle);

        repository.save(battle);
        log.info("Saved battle result: {}", battle.toString());
    }

    private void fillBattleParticipant(Long fighter, Arena arena) {
        BattleParticipant battleParticipant = BattleParticipant.builder()
                .arena(arena)
                .superhero(superheroRepository.getOne(fighter))
                .build();
        battleParticipantRepository.save(battleParticipant);
    }

    @Scheduled(cron = "0 * * * ?")//every hour in 00 minutes
    @Override
    public void restartNotFinishedFight() {
        ArrayList<Arena> notSuccessfulBattle = repository.findAllByFightStatusIsNot2();
        List<Battle> battles = notSuccessfulBattle.stream().map(this::createBattle).collect(Collectors.toList());
        battles.forEach(battle -> rabbitTemplate.convertAndSend(battleResultQueueName, battle));
    }

    private Battle createBattle(Arena arena) {
        int firstFighterId = 0;
        int secondFighterId = 1;

        return Battle.builder()
                .id(arena.getId())
                .superhero1(superheroMapper.toDtoForBattle(
                        superheroRepository.getOne(
                                battleParticipantRepository.findAllByArena(arena).get(firstFighterId).getSuperhero().getId())))
                .superhero2(superheroMapper.toDtoForBattle(
                        superheroRepository.getOne(
                                battleParticipantRepository.findAllByArena(arena).get(secondFighterId).getSuperhero().getId())))
                .build();
    }
}

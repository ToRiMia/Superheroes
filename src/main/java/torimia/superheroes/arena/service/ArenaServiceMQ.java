package torimia.superheroes.arena.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.ArenaMapper;
import torimia.superheroes.arena.ArenaRepository;
import torimia.superheroes.arena.model.dto.*;
import torimia.superheroes.arena.model.entity.Arena;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForBattle;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ArenaServiceMQ implements ArenaService {

    private final ArenaRepository repository;
    private final ArenaMapper mapper;
    private final SuperheroRepository superheroRepository;
    private final SuperheroMapper superheroMapper;

    @Value("${rabbitmq.queue.battle.name}")
    private String battleResultQueueName;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public MessageDto battle(BattleDto dto) {

        Arena arena = Arena.builder()
                .loserId(1L)
                .winnerId(1L)
                .battleTime(0L)
                .attackNumber(0)
                .date(Date.valueOf(LocalDate.now()))
                .fightStatus(FightStatus.NOT_STARTED)
                .build();
        repository.save(arena);

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
        else arena.setFightStatus(FightStatus.FINISHED_UNSUCCESSFUL); // якщо зробити, почне пропускати тільки правильні хороші статуси
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

    @Override
    public void restartNotFinishedFight() {
//        ArrayList<ArenaBattleView> notFinishedBattle = repository.findAllByFightStatusIsNot2();
//        ArrayList<ArenaBattleDto> notFinishedBattleDto = mapper.toDtoFromView(notFinishedBattle);
//        notFinishedBattleDto.stream().map(dto -> dto.)
    }
}

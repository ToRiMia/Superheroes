package torimia.superheroes.arena.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.BattleMapper;
import torimia.superheroes.arena.BattleParticipantRepository;
import torimia.superheroes.arena.BattleRepository;
import torimia.superheroes.arena.model.dto.*;
import torimia.superheroes.arena.model.entity.Battle;
import torimia.superheroes.arena.model.entity.BattleParticipant;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
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
public class BattleServiceMQ implements BattleService {

    private final BattleRepository repository;
    private final BattleMapper mapper;
    private final SuperheroRepository superheroRepository;
    private final BattleParticipantRepository battleParticipantRepository;
    private final SuperheroMapper superheroMapper;
    @Lazy
    private final BattleService service;//self injection for nested transaction

    @Value("${rabbitmq.queue.battle.name}")
    private final String battleQueueName;
    private final RabbitTemplate rabbitTemplate;


    @Override
    public MessageDto battleStart(ReceivingBattleDtoFromUser dto) {
        Battle battle = service.createBattle();

        dto.getFightersIds().forEach(fighter -> fillBattleParticipant(fighter, battle));

        BattleDtoForServer battleDtoForServer = createBattle(dto, battle.getId());

        MessageDto response;
        try {
            rabbitTemplate.convertAndSend(battleQueueName, battleDtoForServer);
            response = MessageDto.builder().message("Battle started").build();

//            throw new RuntimeException();
        } catch (Exception ex) {
            log.error("Error in sending battleDtoForServer request: {}, message: {}", ex, ex.getMessage());
            response = MessageDto.builder().message("Battle not started").build(); // вертати статус 500?
            battle.setBattleStatus(BattleStatus.NOT_STARTED);
            repository.save(battle);
        }

        return response;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public Battle createBattle() {
        Battle battle = Battle.builder()
                .battleTime(0L)
                .attackNumber(0)
                .date(Date.valueOf(LocalDate.now()))
                .battleStatus(BattleStatus.STARTED)
                .build();
        repository.save(battle);
        return battle;
    }

    private void fillBattleParticipant(Long fighter, Battle battle) {
        BattleParticipant battleParticipant = BattleParticipant.builder()
                .battle(battle)
                .superhero(superheroRepository.getOne(fighter))
                .build();
        battleParticipantRepository.save(battleParticipant);
    }

    private BattleDtoForServer createBattle(ReceivingBattleDtoFromUser dto, Long id) {

        List<Long> list = new ArrayList<>(dto.getFightersIds());

        return BattleDtoForServer.builder()
                .id(id)
                .superheroes(getFighters(list))
                .build();
    }

    private List<SuperheroDtoForBattle> getFighters(List<Long> ids) {
        return ids.stream()
                .map(id -> superheroMapper.toDtoForBattle(superheroRepository.getOne(id)))
                .collect(Collectors.toList());
    }

    @RabbitListener(queues = "battle-status")
    public void battleStatus(BattleStatusDto message) {
        Battle battle = repository.getOne(message.getId());
        log.info("Received battle status: {}", message);
        if ((message.getMessage() != BattleStatus.FINISHED_SUCCESSFUL) || (message.getMessage() != BattleStatus.STARTED))
            battle.setBattleStatus(message.getMessage());
        else
            battle.setBattleStatus(BattleStatus.FINISHED_UNSUCCESSFUL);
        repository.save(battle);
        log.info("Saved battle entity with status: {}", battle.toString());
    }

    @RabbitListener(queues = "battle-result")
    public void battleResult(BattleDtoResultFromServerDto result) {
        log.info("BattleDtoForServer result: {}", result);
        saveBattleResult(result);
    }

    @Override
    public void saveBattleResult(BattleDtoResultFromServerDto dto) {
        Battle battle = repository.getOne(dto.getId());
        battle.setBattleStatus(BattleStatus.FINISHED_SUCCESSFUL);
        mapper.toEntityUpdate(dto, battle);

        repository.save(battle);
        log.info("Saved battle result: {}", battle.toString());
    }

    @Scheduled(cron = "0 0 */1 * * *")//every hour in 00 minutes 00 sec
    //@Scheduled(fixedDelay = 60000)
    @Override
    public void restartNotFinishedBattle() {
        ArrayList<Battle> notSuccessfulBattle = repository.findAllByBattleStatusIsNotFINISHED_SUCCESSFUL();
        List<BattleDtoForServer> battleDtoForServers = notSuccessfulBattle.stream().map(this::createBattle).collect(Collectors.toList());
        battleDtoForServers.forEach(battleDtoForServer -> rabbitTemplate.convertAndSend(battleQueueName, battleDtoForServer));
        log.info("Sent not finished battle");
    }

    private BattleDtoForServer createBattle(Battle battle) {
        return BattleDtoForServer.builder()
                .id(battle.getId())
                .superheroes(superheroMapper.toSuperheroDtoForBattle(battleParticipantRepository.findAllByBattle(battle)))
                .build();
    }
}

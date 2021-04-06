package torimia.superheroes.arena.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.BattleFactory;
import torimia.superheroes.arena.BattleMapper;
import torimia.superheroes.arena.BattleParticipantRepository;
import torimia.superheroes.arena.BattleRepository;
import torimia.superheroes.arena.model.dto.*;
import torimia.superheroes.arena.model.entity.Battle;
import torimia.superheroes.exceptions.BattleException;
import torimia.superheroes.exceptions.BattleNotStartedException;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForBattle;

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
    private final BattleFactory factory;

    @Value("${rabbitmq.queue.battle.name}")
    private final String battleQueueName;

    private final RabbitTemplate rabbitTemplate;


    @Override
    public MessageDto battleStart(ReceivingBattleDtoFromUser dto) {
        Battle battle = factory.createBattle();

        dto.getFightersIds().forEach(fighter -> factory.fillBattleParticipant(fighter, battle));

        BattleDtoForServer battleDtoForServer = createBattle(dto, battle.getId());

        MessageDto response;
        try {
            rabbitTemplate.convertAndSend(battleQueueName, battleDtoForServer);
            response = MessageDto.builder().message("Battle started").build();

            if (new Random().nextInt(10) == 5) {
                throw new BattleException("Generated exception for checking service work during fatal battle ending");
            }

        } catch (Exception ex) {
            battle.setBattleStatus(BattleStatus.NOT_STARTED);
            repository.save(battle);
            log.error("Error in sending battleDtoForServer request: {}, message: {}", ex, ex.getMessage());
            throw new BattleNotStartedException("Battle not started");
        }

        return response;
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

    @RabbitListener(queues = "${rabbitmq.queue.battle-status.name}", errorHandler = "rabbitMQExceptionHandler")
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

    @RabbitListener(queues = "${rabbitmq.queue.battle-result.name}", errorHandler = "rabbitMQExceptionHandler")
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

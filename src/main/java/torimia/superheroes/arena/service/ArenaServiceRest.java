package torimia.superheroes.arena.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.ArenaMapper;
import torimia.superheroes.arena.ArenaRepository;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.dto.Battle;
import torimia.superheroes.arena.model.dto.BattleDto;
import torimia.superheroes.arena.model.dto.FightStatus;
import torimia.superheroes.arena.model.entity.Arena;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForBattle;

import java.security.cert.CertPathValidatorException;
import java.sql.Date;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
//@Service
public class ArenaServiceRest implements ArenaService {

    private final ArenaRepository repository;
    private final ArenaMapper mapper;
    private final SuperheroRepository superheroRepository;
    private final SuperheroMapper superheroMapper;

    private final RestTemplate restTemplate;
    private final static String URL_BATTLE = "/battle";

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

        Battle battle = createBattle(dto, arena.getId());

        MessageDto response;
        try {
            HttpEntity<Battle> request = new HttpEntity<>(battle);
            response = restTemplate.postForObject(URL_BATTLE, request, MessageDto.class);
            log.info("Response {}", response);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            response = MessageDto.builder().message("Fight not started").build();
            arena.setFightStatus(FightStatus.NOT_STARTED);
            repository.save(arena);
        }

        return response;
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
    }

    @Override
    public void restartNotFinishedFight() {

    }
}
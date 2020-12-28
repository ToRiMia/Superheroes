package torimia.superheroes.arena.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.ArenaMapper;
import torimia.superheroes.arena.ArenaRepository;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.dto.BattleDto;
import torimia.superheroes.arena.model.entity.Arena;
import torimia.superheroes.arena.model.entity.Battle;
import torimia.superheroes.arena.model.enums.FightStatus;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForBattle;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArenaServiceImpl implements ArenaService {

    private final ArenaRepository repository;
    private final ArenaMapper mapper;
    private final SuperheroRepository superheroRepository;
    private final SuperheroMapper superheroMapper;

    private final RestTemplate restTemplate;
    private final static String URL_BATTLE = "/battle";

    @Override
    public MessageDto battle(BattleDto dto) {

        Arena arena = Arena.builder()
                .loserId(-1L)
                .winnerId(-1L)
                .battleTime(0L)
                .attackNumber(0)
                .date(Date.valueOf(LocalDate.now()))
                .fightStatus(FightStatus.STARTED)
                .build();
        repository.save(arena);

        Battle battle = createBattle(dto, arena.getId());

        HttpEntity<Battle> request = new HttpEntity<>(battle);
        MessageDto response = restTemplate.postForObject(URL_BATTLE, request, MessageDto.class);

        log.info("Response {}", response);

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


        repository.save(mapper.toEntity(dto));
    }
}

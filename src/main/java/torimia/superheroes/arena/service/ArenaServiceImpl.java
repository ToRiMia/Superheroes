package torimia.superheroes.arena.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.ArenaMapper;
import torimia.superheroes.arena.ArenaRepository;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.dto.BattleDto;
import torimia.superheroes.arena.model.entity.Battle;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForBattle;

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
        Battle battle = createBattle(dto);

        HttpEntity<Battle> request = new HttpEntity<>(battle);
        MessageDto response = restTemplate.postForObject(URL_BATTLE, request, MessageDto.class);

        log.info("Response {}", response);

        return response;
    }

    private Battle createBattle(BattleDto dto) {
        Battle battle = new Battle();
        battle.setSuperhero1(getFighter(dto.getFirstFighterId()));
        battle.setSuperhero2(getFighter(dto.getSecondFighterId()));
        return battle;
    }

    private SuperheroDtoForBattle getFighter(Long id) {
        return superheroMapper.toDtoForBattle(superheroRepository.getOne(id));
    }

    @Override
    public void saveBattleResult(ArenaBattleDto dto) {
        repository.save(mapper.toEntity(dto));
    }
}

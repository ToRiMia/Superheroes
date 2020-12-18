package torimia.superheroes.arena.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import torimia.superheroes.arena.ArenaMapper;
import torimia.superheroes.arena.ArenaRepository;
import torimia.superheroes.arena.model.entity.Battle;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.dto.IdRequest;

@RequiredArgsConstructor
@Service
public class ArenaServiceImpl implements ArenaService {

    private final ArenaRepository repository;
    private final ArenaMapper mapper;
    private final SuperheroRepository superheroRepository;
    private final SuperheroMapper superheroMapper;

    @Override
    public ArenaBattleDto battle(Long superheroId, IdRequest id) {
        RestTemplate restTemplate = new RestTemplate();
        Battle battle = new Battle();
        battle.setSuperhero1(superheroMapper.toDtoForBattle(superheroRepository.getOne(superheroId)));
        battle.setSuperhero2(superheroMapper.toDtoForBattle(superheroRepository.getOne(id.getId())));

        String url = "http://localhost:8081/battle";
        HttpEntity<Battle> request = new HttpEntity<>(battle);
        ResponseEntity<ArenaBattleDto> response = restTemplate
                .exchange(url, HttpMethod.POST, request, ArenaBattleDto.class);
        repository.save(mapper.toEntity(response.getBody()));
        return response.getBody();
    }

}

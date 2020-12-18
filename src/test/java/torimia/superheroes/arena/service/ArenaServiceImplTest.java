package torimia.superheroes.arena.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import torimia.superheroes.arena.ArenaMapper;
import torimia.superheroes.arena.ArenaRepository;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.entity.Battle;
import torimia.superheroes.award.AwardRepository;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.superhero.model.dto.SuperheroDto;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForBattle;
import torimia.superheroes.superhero.service.SuperheroServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArenaServiceImplTest {

    @InjectMocks
    private ArenaServiceImpl service;
    @Mock
    private ArenaRepository repository;
    @Mock
    private SuperheroRepository superheroRepository;
    @Mock
    private ArenaMapper mapper;
    @Mock
    private SuperheroMapper superheroMapper;


    @BeforeEach
    void setUp() {
    }

//    @Test
//    void battle() {
//        Long fighter1Id = 1L;
//        Superhero superhero1 = new Superhero();
//        when(superheroRepository.getOne(fighter1Id)).thenReturn(superhero1);
//
//        SuperheroDtoForBattle superheroDtoForBattle = new SuperheroDtoForBattle();
//        when(superheroMapper.toDtoForBattle(superhero1)).thenReturn(superheroDtoForBattle);
//
//        Battle mockBattle = mock(Battle.class);
//        verify(mockBattle).setSuperhero1(superheroDtoForBattle);
//        verify(mockBattle).setSuperhero2(superheroDtoForBattle);
//
//        String url = "http://localhost:8081/battle";
//        RestTemplate mockRestTemplate = mock(RestTemplate.class);
//        verify(mockRestTemplate).exchange(url, HttpMethod.POST,
//                new HttpEntity<>(mockBattle), ArenaBattleDto.class);
//
//        ArenaBattleDto arenaBattleDto = new ArenaBattleDto();
//        ResponseEntity<ArenaBattleDto> response = new
//                ResponseEntity<ArenaBattleDto>(HttpStatus.OK, arenaBattleDto.toString());
//    }
}
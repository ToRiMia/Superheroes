package torimia.superheroes.arena.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import torimia.superheroes.arena.ArenaMapper;
import torimia.superheroes.arena.ArenaRepository;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;

@ExtendWith(MockitoExtension.class)
class ArenaServiceRestTest {

    @InjectMocks
    private ArenaServiceRest service;
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
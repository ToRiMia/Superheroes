package torimia.superheroes.superhero.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import torimia.superheroes.TestingUtils;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForTop;
import torimia.superheroes.superhero.model.dto.SuperheroViewForTop;
import torimia.superheroes.award.AwardRepository;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.service.SuperheroServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceImplBiggestAmountFriendsOrEnemiesTest {

    @InjectMocks
    private SuperheroServiceImpl service;
    @Mock
    private SuperheroRepository repository;
    @Mock
    private AwardRepository awardRepository;
    @Mock
    private SuperheroMapper mapper;

    private List<SuperheroViewForTop> superheroes;

    private SuperheroDtoForTop dtoForTop;

    private List<SuperheroDtoForTop> superheroesForResponse;

    private final int superheroesAmount = 3;

    @BeforeEach
    void setUp() {
        dtoForTop = new SuperheroDtoForTop();
        superheroes = TestingUtils
                .createListOf(3, SuperheroDtoForTop::new);
        superheroesForResponse = TestingUtils
                .createListOf(3, SuperheroDtoForTop::new);

        when(mapper.toDtoForTop(any(SuperheroViewForTop.class))).thenReturn(dtoForTop);
    }

    @Test
    void getSuperheroesWithTheBiggestAmountsOfFriends() {
        when(repository.getSuperheroesWithTheBiggestAmountsOfFriends(superheroesAmount))
                .thenReturn(superheroes);

        List<SuperheroDtoForTop> superheroesResponse = service
                .getSuperheroesWithTheBiggestAmountsOfFriends(superheroesAmount);

        assertThat(superheroesResponse)
                .isEqualTo(superheroesForResponse)
                .hasSize(superheroesAmount);
    }

    @Test
    void getSuperheroesWithTheBiggestAmountsOfEnemies() {
        when(repository.getSuperheroesWithTheBiggestAmountsOfEnemies(superheroesAmount))
                .thenReturn(superheroes);

        List<SuperheroDtoForTop> superheroesResponse = service
                .getSuperheroesWithTheBiggestAmountsOfEnemies(superheroesAmount);

        assertThat(superheroesResponse)
                .isEqualTo(superheroes)
                .hasSize(superheroesAmount);
    }

}
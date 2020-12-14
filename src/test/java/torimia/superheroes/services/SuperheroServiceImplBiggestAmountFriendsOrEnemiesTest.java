package torimia.superheroes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import torimia.superheroes.mappers.SuperheroMapper;
import torimia.superheroes.model.dto.SuperheroDtoForTop;
import torimia.superheroes.model.dto.SuperheroViewForTop;
import torimia.superheroes.repo.AwardRepository;
import torimia.superheroes.repo.SuperheroRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    private final int superheroesAmount = 3;

    @BeforeEach
    void setUp() {
        superheroes = Arrays.asList(new SuperheroDtoForTop(),
                new SuperheroDtoForTop(), new SuperheroDtoForTop());
    }

    @Test
    void getSuperheroesWithTheBiggestAmountsOfFriends() {
        when(repository.getSuperheroesWithTheBiggestAmountsOfFriends(superheroesAmount))
                .thenReturn(superheroes);

        List<SuperheroViewForTop> superheroesResponse = service
                .getSuperheroesWithTheBiggestAmountsOfFriends(superheroesAmount);

        assertThat(superheroesResponse)
                .isEqualTo(superheroes)
                .hasSize(superheroesAmount);
    }

    @Test
    void getSuperheroesWithTheBiggestAmountsOfEnemies() {
        when(repository.getSuperheroesWithTheBiggestAmountsOfEnemies(superheroesAmount))
                .thenReturn(superheroes);

        List<SuperheroViewForTop> superheroesResponse = service
                .getSuperheroesWithTheBiggestAmountsOfEnemies(superheroesAmount);

        assertThat(superheroesResponse)
                .isEqualTo(superheroes)
                .hasSize(superheroesAmount);
    }

}
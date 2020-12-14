package torimia.superheroes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import torimia.superheroes.mappers.SuperheroMapper;
import torimia.superheroes.model.dto.AwardDto;
import torimia.superheroes.model.dto.AwardView;
import torimia.superheroes.model.dto.SuperheroAwardsDto;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.model.entity.Superhero;
import torimia.superheroes.repo.AwardRepository;
import torimia.superheroes.repo.SuperheroRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceImplGetAwardsTest {

    @InjectMocks
    private SuperheroServiceImpl service;
    @Mock
    private SuperheroRepository repository;
    @Mock
    private AwardRepository awardRepository;
    @Mock
    private SuperheroMapper mapper;

    private Superhero superhero;
    private final Long SUPERHERO_ID = 1L;
    private PageRequest pageRequest;
    private List<AwardView> awards;
    PageImpl<AwardView> page;

    @BeforeEach
    void setUp() {
        superhero = new Superhero();
        pageRequest = PageRequest.of(0, 5, Sort.unsorted());
        awards = Arrays.asList(new AwardDto(), new AwardDto(),
                new AwardDto(), new AwardDto(), new AwardDto());
        page = new PageImpl<>(awards);
    }

    @Test
    void getSuperheroTop5Awards() {
        when(repository.getSuperheroAwards(SUPERHERO_ID, pageRequest)).thenReturn(page);

        when(repository.getOne(SUPERHERO_ID)).thenReturn(superhero);

        List<AwardDto> superheroAwards = Arrays.asList(new AwardDto(), new AwardDto(),
                new AwardDto(), new AwardDto(), new AwardDto());
        SuperheroAwardsDto superheroAwardsDto = new SuperheroAwardsDto();
        superheroAwardsDto.setAwards(superheroAwards);
        when(mapper.toDtoSuperheroAwards(superhero, awards)).thenReturn(superheroAwardsDto);

        SuperheroAwardsDto superheroAwardsDtoResponse = service.getSuperheroTop5Awards(SUPERHERO_ID);

        assertThat(superheroAwardsDtoResponse.getAwards()).hasSize(5);
    }

    @Test
    void getSuperheroAwards() {
        when(repository.getSuperheroAwards(SUPERHERO_ID, pageRequest)).thenReturn(page);

        Page<AwardView> pageResponse = service.getSuperheroAwards(SUPERHERO_ID, pageRequest);

        assertThat(pageResponse.getContent())
                .hasSize(5)
                .isEqualTo(awards);
    }

}
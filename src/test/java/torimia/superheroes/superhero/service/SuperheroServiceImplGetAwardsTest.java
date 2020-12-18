package torimia.superheroes.superhero.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.award.model.dto.AwardDto;
import torimia.superheroes.award.model.dto.AwardView;
import torimia.superheroes.superhero.model.dto.SuperheroAwardsDto;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.award.AwardRepository;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.service.SuperheroServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static torimia.superheroes.TestingUtils.createListOf;

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
        awards = createListOf(5,AwardDto::new);
        page = new PageImpl<>(awards);
    }

    @Test
    void getSuperheroTop5Awards() {
        when(repository.getSuperheroAwards(SUPERHERO_ID, pageRequest)).thenReturn(page);

        when(repository.getOne(SUPERHERO_ID)).thenReturn(superhero);

        List<AwardDto> superheroAwards = createListOf(5, AwardDto::new);

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
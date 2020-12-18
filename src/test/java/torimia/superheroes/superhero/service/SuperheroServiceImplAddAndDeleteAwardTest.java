package torimia.superheroes.superhero.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.model.dto.IdRequest;
import torimia.superheroes.superhero.model.dto.SuperheroDto;
import torimia.superheroes.award.model.entity.Award;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.award.AwardRepository;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.service.SuperheroServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceImplAddAndDeleteAwardTest {

    @InjectMocks
    private SuperheroServiceImpl service;
    @Mock
    private SuperheroRepository repository;
    @Mock
    private AwardRepository awardRepository;
    @Mock
    private SuperheroMapper mapper;

    private final IdRequest awardId = IdRequest.of(2L);

    private final Long superheroId = 1L;

    private Superhero mockSuperhero;

    private Award award;

    private SuperheroDto dtoForResponse;

    @BeforeEach
    void setUp() {
        mockSuperhero = mock(Superhero.class);
        award = new Award();
        dtoForResponse = new SuperheroDto();

        when(repository.getOne(superheroId)).thenReturn(mockSuperhero);
        when(awardRepository.getOne(awardId.getId())).thenReturn(award);
        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);
        when(mapper.toDto(mockSuperhero)).thenReturn(dtoForResponse);
    }


    @Test
    void addAward() {
        SuperheroDto dtoResponse = service.addAward(superheroId, awardId);

        assertThat(dtoResponse).isEqualTo(dtoForResponse);

        verify(mockSuperhero).addAward(award);
    }

    @Test
    void deleteAward() {
        SuperheroDto dtoResponse = service.deleteAward(superheroId, awardId);

        assertThat(dtoResponse).isEqualTo(dtoForResponse);

        verify(mockSuperhero).deleteAward(award);
    }

}
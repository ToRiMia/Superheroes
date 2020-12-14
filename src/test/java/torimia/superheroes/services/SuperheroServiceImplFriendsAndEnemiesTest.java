package torimia.superheroes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import torimia.superheroes.mappers.SuperheroMapper;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.model.entity.Superhero;
import torimia.superheroes.repo.SuperheroRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceImplFriendsAndEnemiesTest {

    @InjectMocks
    private SuperheroServiceImpl service;
    @Mock
    private SuperheroRepository repository;
    @Mock
    private SuperheroMapper mapper;

    private final Long SUPERHERO_ID = 1L;

    private Superhero mockSuperhero;

    private Superhero anotherSuperhero;

    SuperheroDto dtoForResponse;

    private IdRequest ID_REQUEST;

    @BeforeEach()
    void setUp() {
        mockSuperhero = mock(Superhero.class);
        when(repository.getOne(SUPERHERO_ID)).thenReturn(mockSuperhero);

        ID_REQUEST = IdRequest.of(2L);
        anotherSuperhero = new Superhero();
        when(repository.getOne(ID_REQUEST.getId())).thenReturn(anotherSuperhero);

        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);

        dtoForResponse = new SuperheroDto();
        when(mapper.toDto(mockSuperhero)).thenReturn(dtoForResponse);
    }

    @Test()
    void addNewFriend() {
        SuperheroDto dtoResponse = service.addFriend(SUPERHERO_ID, ID_REQUEST);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).addFriend(anotherSuperhero);
    }

    @Test
    void deleteFriend() {
        SuperheroDto dtoResponse = service.deleteFriend(SUPERHERO_ID, ID_REQUEST);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).deleteFriend(anotherSuperhero);
    }

    @Test()
    void addEnemy() {
        SuperheroDto dtoResponse = service.addEnemy(SUPERHERO_ID, ID_REQUEST);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).addEnemy(anotherSuperhero);
    }

    @Test
    void deleteEnemy() {
        SuperheroDto dtoResponse = service.deleteEnemy(SUPERHERO_ID, ID_REQUEST);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).deleteEnemy(anotherSuperhero);
    }

}
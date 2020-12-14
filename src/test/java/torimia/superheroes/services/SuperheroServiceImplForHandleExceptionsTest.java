package torimia.superheroes.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import torimia.superheroes.exceptions.AddingToListException;
import torimia.superheroes.mappers.SuperheroMapper;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.repo.SuperheroRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceImplForHandleExceptionsTest {

    @InjectMocks
    private SuperheroServiceImpl service;
    @Mock
    private SuperheroRepository repository;
    @Mock
    private SuperheroMapper mapper;

    private final Long SUPERHERO_ID = 1L;

    private final IdRequest ID_REQUEST = IdRequest.of(SUPERHERO_ID);

    @Test()
    void addNewFriendWithAddingToListException() throws AddingToListException {
        assertThrows(AddingToListException.class,
                () -> service.addNewFriend(SUPERHERO_ID, ID_REQUEST));
    }

    @Test()
    void addEnemyWithAddingToListException() throws AddingToListException {
        assertThrows(AddingToListException.class,
                () -> service.addEnemy(SUPERHERO_ID, ID_REQUEST));
    }

}
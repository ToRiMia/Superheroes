package torimia.superheroes.services;

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
import torimia.superheroes.exceptions.AddingToListException;
import torimia.superheroes.mappers.SuperheroMapper;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.model.entity.Superhero;
import torimia.superheroes.repo.AwardRepository;
import torimia.superheroes.repo.SuperheroRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceImplTest {

    @InjectMocks
    private SuperheroServiceImpl service;
    @Mock
    private SuperheroRepository repository;
    @Mock
    private AwardRepository awardRepository;
    @Mock
    private SuperheroMapper mapper;

    private Superhero superhero;

    private SuperheroDto dto;

    @BeforeEach
    void setUp() {
        superhero = new Superhero();
        dto = new SuperheroDto();
    }

    @Test
    void getPage() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.unsorted());

        List<Superhero> superheroes = Arrays.asList(new Superhero(), new Superhero(),
                new Superhero(), new Superhero(), new Superhero());
        PageImpl<Superhero> pageResponse = new PageImpl<>(superheroes);

        when(repository.findAll(pageRequest)).thenReturn(pageResponse);
        when(mapper.toDto(any(Superhero.class))).thenReturn(new SuperheroDto());

        Page<SuperheroDto> page = service.getPage(pageRequest);

        assertThat(page.getContent()).hasSize(5);
    }

    @Test
    void getById() {
        Long id = 1L;
        when(repository.getOne(id)).thenReturn(superhero);
        when(mapper.toDto(superhero)).thenReturn(dto);

        SuperheroDto dtoResponse = service.getById(id);

        assertThat(dtoResponse).isEqualTo(dto);
    }

    @Test
    void create() {
        Superhero mockSuperhero = mock(Superhero.class);
        when(mapper.toEntity(dto)).thenReturn(mockSuperhero);

        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);
        when(mapper.toDto(mockSuperhero)).thenReturn(dto);

        SuperheroDto dtoResponse = service.create(dto);

        assertThat(dtoResponse).isEqualTo(dto);

        verify(mockSuperhero).setId(null);
    }


    @Test
    void update() {
        Long id = 1L;
        when(repository.getOne(id)).thenReturn(superhero);

        when(repository.save(superhero)).thenReturn(superhero);

        SuperheroDto dtoForResponse = new SuperheroDto();
        when(mapper.toDto(superhero)).thenReturn(dtoForResponse);

        SuperheroDto dtoResponse = service.update(id, dto);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mapper).toEntityUpdate(dto, superhero);
    }

    @Test
    void delete() {
        Long id = 1L;

        service.delete(id);

        verify(repository).deleteById(id);
    }


    @Test()
    void addNewFriendWithAddingToListException() throws AddingToListException {
        Long superheroId = 1L;
        IdRequest friendId = IdRequest.of(superheroId);

        assertThrows(AddingToListException.class,
                () -> service.addNewFriend(superheroId, friendId));
    }

    @Test()
    void addNewFriend() {
        IdRequest friendId = IdRequest.of(2L);

        Superhero mockSuperhero = mock(Superhero.class);
        Long superheroId = 1L;
        when(repository.getOne(superheroId)).thenReturn(mockSuperhero);

        Superhero friend = new Superhero();
        when(repository.getOne(friendId.getId())).thenReturn(friend);

        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);

        SuperheroDto dtoForResponse = new SuperheroDto();
        when(mapper.toDto(mockSuperhero)).thenReturn(dtoForResponse);

        SuperheroDto dtoResponse = service.addNewFriend(superheroId, friendId);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).addFriend(friend);
    }

    @Test
    void deleteFriend() {
        IdRequest friendId = IdRequest.of(2L);

        Superhero mockSuperhero = mock(Superhero.class);
        Long superheroId = 1L;
        when(repository.getOne(superheroId)).thenReturn(mockSuperhero);

        Superhero friend = new Superhero();
        when(repository.getOne(friendId.getId())).thenReturn(friend);

        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);

        SuperheroDto dtoForResponse = new SuperheroDto();
        when(mapper.toDto(mockSuperhero)).thenReturn(dtoForResponse);

        SuperheroDto dtoResponse = service.deleteFriend(superheroId, friendId);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).deleteFriend(friend);


    }

    @Test()
    void addEnemyWithAddingToListException() throws AddingToListException {
        Long superheroId = 1L;
        IdRequest enemyId = IdRequest.of(superheroId);

        assertThrows(AddingToListException.class,
                () -> service.addEnemy(superheroId, enemyId));
    }

    @Test()
    void addEnemy() {
        IdRequest enemyId = IdRequest.of(2L);

        Superhero mockSuperhero = mock(Superhero.class);
        Long superheroId = 1L;
        when(repository.getOne(superheroId)).thenReturn(mockSuperhero);

        Superhero enemy = new Superhero();
        when(repository.getOne(enemyId.getId())).thenReturn(enemy);

        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);

        SuperheroDto dtoForResponse = new SuperheroDto();
        when(mapper.toDto(mockSuperhero)).thenReturn(dtoForResponse);

        SuperheroDto dtoResponse = service.addEnemy(superheroId, enemyId);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).addEnemy(enemy);
    }

    @Test
    void deleteEnemy() {
        IdRequest enemyId = IdRequest.of(2L);

        Superhero mockSuperhero = mock(Superhero.class);
        Long superheroId = 1L;
        when(repository.getOne(superheroId)).thenReturn(mockSuperhero);

        Superhero enemy = new Superhero();
        when(repository.getOne(enemyId.getId())).thenReturn(enemy);

        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);

        SuperheroDto dtoForResponse = new SuperheroDto();
        when(mapper.toDto(mockSuperhero)).thenReturn(dtoForResponse);

        SuperheroDto dtoResponse = service.deleteEnemy(superheroId, enemyId);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).deleteEnemy(enemy);
    }
}
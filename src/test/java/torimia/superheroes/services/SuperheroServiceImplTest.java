package torimia.superheroes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import torimia.superheroes.exceptions.AddingToListException;
import torimia.superheroes.mappers.SuperheroMapper;
import torimia.superheroes.model.dto.*;
import torimia.superheroes.model.entity.Award;
import torimia.superheroes.model.entity.Superhero;
import torimia.superheroes.repo.AwardRepository;
import torimia.superheroes.repo.SuperheroRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    void getSuperheroesWithTheBiggestAmountsOfFriends() {
        List<SuperheroViewForTop> superheroesWithFriends = Arrays.asList(new SuperheroDtoForTop(),
                new SuperheroDtoForTop(), new SuperheroDtoForTop());

        int superheroesAmount = 3;
        when(repository.getSuperheroesWithTheBiggestAmountsOfFriends(superheroesAmount))
                .thenReturn(superheroesWithFriends);

        List<SuperheroViewForTop> superheroesResponse = repository
                .getSuperheroesWithTheBiggestAmountsOfFriends(superheroesAmount);

        assertThat(superheroesResponse)
                .isEqualTo(superheroesWithFriends)
                .hasSize(superheroesAmount);
    }

    @Test
    void getSuperheroesWithTheBiggestAmountsOfEnemies() {
        List<SuperheroViewForTop> superheroesWithEnemies = Arrays.asList(new SuperheroDtoForTop(),
                new SuperheroDtoForTop(), new SuperheroDtoForTop());

        int superheroesAmount = 3;
        when(repository.getSuperheroesWithTheBiggestAmountsOfEnemies(superheroesAmount))
                .thenReturn(superheroesWithEnemies);

        List<SuperheroViewForTop> superheroesResponse = repository
                .getSuperheroesWithTheBiggestAmountsOfEnemies(superheroesAmount);

        assertThat(superheroesResponse)
                .isEqualTo(superheroesWithEnemies)
                .hasSize(superheroesAmount);
    }

    @Test
    void addAward() {
        IdRequest awardId = IdRequest.of(2L);

        Superhero mockSuperhero = mock(Superhero.class);
        Long superheroId = 1L;
        when(repository.getOne(superheroId)).thenReturn(mockSuperhero);

        Award award = new Award();
        when(awardRepository.getOne(awardId.getId())).thenReturn(award);

        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);

        SuperheroDto dtoForResponse = new SuperheroDto();
        when(mapper.toDto(mockSuperhero)).thenReturn(dtoForResponse);

        SuperheroDto dtoResponse = service.addAward(superheroId, awardId);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).addAward(award);
    }

    @Test
    void deleteAward() {
        IdRequest awardId = IdRequest.of(2L);

        Superhero mockSuperhero = mock(Superhero.class);
        Long superheroId = 1L;
        when(repository.getOne(superheroId)).thenReturn(mockSuperhero);

        Award award = new Award();
        when(awardRepository.getOne(awardId.getId())).thenReturn(award);

        when(repository.save(mockSuperhero)).thenReturn(mockSuperhero);

        SuperheroDto dtoForResponse = new SuperheroDto();
        when(mapper.toDto(mockSuperhero)).thenReturn(dtoForResponse);

        SuperheroDto dtoResponse = service.deleteAward(superheroId, awardId);

        assertThat(dtoResponse)
                .isEqualTo(dtoForResponse);

        verify(mockSuperhero).deleteAward(award);
    }

    @Test
    void getSuperheroTop5Awards() {
        Long superheroId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.unsorted());

        List<AwardView> awards = Arrays.asList(new AwardDto(), new AwardDto(),
                new AwardDto(), new AwardDto(), new AwardDto());
        PageImpl<AwardView> pageResponse = new PageImpl<>(awards);
        when(repository.getSuperheroAwards(superheroId, pageRequest)).thenReturn(pageResponse);

        when(repository.getOne(superheroId)).thenReturn(superhero);

        List<AwardDto> superheroAwards = Arrays.asList(new AwardDto(), new AwardDto(),
                new AwardDto(), new AwardDto(), new AwardDto());
        SuperheroAwardsDto superheroAwardsDto = new SuperheroAwardsDto();
        superheroAwardsDto.setAwards(superheroAwards);
        when(mapper.toDtoSuperheroAwards(superhero, awards))
                .thenReturn(superheroAwardsDto);

        SuperheroAwardsDto superheroAwardsDtoResponse = service.getSuperheroTop5Awards(superheroId);

        assertThat(superheroAwardsDtoResponse.getAwards()).hasSize(5);
    }

    @Test
    void getSuperheroAwards() {
        Long superheroId = 1L;
        Pageable pageable = PageRequest.of(0, 5, Sort.unsorted());

        List<AwardView> awards = Arrays.asList(new AwardDto(), new AwardDto(),
                new AwardDto(), new AwardDto(), new AwardDto());
        PageImpl<AwardView> page = new PageImpl<>(awards);

        when(repository.getSuperheroAwards(superheroId, pageable)).thenReturn(page);

        Page<AwardView> pageResponse = service.getSuperheroAwards(superheroId, pageable);

        assertThat(pageResponse.getContent()).hasSize(5);
    }
}
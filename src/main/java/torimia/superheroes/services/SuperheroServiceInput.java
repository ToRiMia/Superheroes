package torimia.superheroes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import torimia.superheroes.exceptions.AddingToListException;
import torimia.superheroes.mappers.AwardMapper;
import torimia.superheroes.mappers.SuperheroMapper;
import torimia.superheroes.model.dto.*;
import torimia.superheroes.model.entity.Award;
import torimia.superheroes.model.entity.Superhero;
import torimia.superheroes.repo.AwardRepository;
import torimia.superheroes.repo.SuperheroRepository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SuperheroServiceInput implements SuperheroService {

    private final SuperheroRepository repository;
    private final AwardRepository awardRepository;
    private final SuperheroMapper mapper;
    private final AwardMapper awardMapper;

    @Override
    public Page<SuperheroDto> getPage(Pageable page) {
        Page<Superhero> superheroes = repository.findAll(page);
        return superheroes.map(mapper::toDto);
    }

    @Override
    public SuperheroDto create(SuperheroDto dto) {
        Superhero superhero = mapper.toEntity(dto);
        superhero.setId(null);
        return mapper.toDto(repository.save(superhero));
    }

    @Override
    public SuperheroDto update(Long id, SuperheroDto dto) {
        Superhero superhero = repository.getOne(id);
        mapper.toEntityUpdate(dto, superhero);
        return mapper.toDto(repository.save(superhero));
    }

    @Override
    public void delete(Long id) {
        Superhero superhero = repository.getOne(id);
        repository.delete(superhero);
    }

    @Override
    public SuperheroDto addNewFriend(Long superheroId, IdRequest id) {
        if (superheroId.equals(id.getId())) {
            throw new AddingToListException("It's forbidden to add yourself to the list of your friends or enemies");
        }
        Superhero superhero = repository.getOne(superheroId);
        Superhero friend = repository.getOne(id.getId());
        superhero.addFriend(friend);
        return mapper.toDto(repository.save(superhero));
    }

    @Override
    public SuperheroDto deleteFriend(Long superheroId, IdRequest id) {
        Superhero superhero = repository.getOne(superheroId);
        Superhero friend = repository.getOne(id.getId());
        superhero.deleteFriend(friend);
        return mapper.toDto(repository.save(superhero));
    }

    @Override
    public SuperheroDto addEnemy(Long superheroId, IdRequest id) {
        if (superheroId.equals(id.getId())) {
            throw new AddingToListException("It's forbidden to add yourself to the list of your friends or enemies");
        }
        Superhero superhero = repository.getOne(superheroId);
        Superhero enemy = repository.getOne(id.getId());
        superhero.addEnemy(enemy);
        return mapper.toDto(repository.save(superhero));
    }

    @Override
    public SuperheroDto deleteEnemy(Long superheroId, IdRequest id) {
        Superhero superhero = repository.getOne(superheroId);
        Superhero enemy = repository.getOne(id.getId());
        superhero.deleteEnemy(enemy);
        return mapper.toDto(repository.save(superhero));
    }

    @Override
    public List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfFriends(Integer amountOfSuperhero) {
        return repository.getSuperheroesWithTheBiggestAmountsOfFriends(amountOfSuperhero);
    }

    @Override
    public List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfEnemies(Integer amountOfSuperhero) {
        return repository.getSuperheroesWithTheBiggestAmountsOfEnemies(amountOfSuperhero);
    }

    @Override
    public SuperheroDto addAward(Long superheroId, IdRequest id) {
        Superhero superhero = repository.getOne(superheroId);
        Award award = awardRepository.getOne(id.getId());
        superhero.addAward(award);
        return mapper.toDto(repository.save(superhero));
    }

    @Override
    public SuperheroDto deleteAward(Long superheroId, IdRequest id) {
        Superhero superhero = repository.getOne(superheroId);
        Award award = awardRepository.getOne(id.getId());
        superhero.deleteAward(award);
        return mapper.toDto(repository.save(superhero));
    }

    @Override
    public List<AwardView> getSuperheroAwards(Long superheroId) {
        return repository.getSuperheroAwards(superheroId);
    }
}

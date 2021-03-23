package torimia.superheroes.superhero.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import torimia.superheroes.award.AwardRepository;
import torimia.superheroes.award.model.dto.AwardView;
import torimia.superheroes.award.model.entity.Award;
import torimia.superheroes.exceptions.AddingToListException;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.superhero.model.dto.IdRequest;
import torimia.superheroes.superhero.model.dto.SuperheroAwardsDto;
import torimia.superheroes.superhero.model.dto.SuperheroDto;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForTop;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SuperheroServiceImpl implements SuperheroService {

    private final SuperheroRepository repository;
    private final AwardRepository awardRepository;
    private final SuperheroMapper mapper;
    private final UserRepository userRepository;

    @Override
    public Page<SuperheroDto> getPage(Pageable page) {
        Page<Superhero> superheroes = repository.findAll(page);
        return superheroes.map(mapper::toDto);
    }

    @Override
    public SuperheroDto getById(Long id) {
        return mapper.toDto(repository.getOne(id));
    }

    @Override
    public SuperheroDto create(SuperheroDto dto) {
        Superhero superhero = mapper.toEntity(dto);
        superhero.setId(null);
        Superhero savedSuperhero = repository.save(superhero);

        User user = userRepository.findUserById(getUserId()).orElseThrow(EntityNotFoundException::new);
        user.addSuperhero(savedSuperhero);
        userRepository.save(user);

        return mapper.toDto(savedSuperhero);
    }

    private String getUserId() {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getName();
    }

    @Override
    public SuperheroDto update(Long id, SuperheroDto dto) {
        Superhero superhero = repository.getOne(id);
        mapper.toEntityUpdate(dto, superhero);
        return mapper.toDto(repository.save(superhero));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SuperheroDto addFriend(Long superheroId, IdRequest id) {
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

    @CachePut(value = "getTopSuperheroWithFriends")
    @Override
    public List<SuperheroDtoForTop> getSuperheroesWithTheBiggestAmountsOfFriends(Integer amountOfSuperhero) {
        return repository.getSuperheroesWithTheBiggestAmountsOfFriends(amountOfSuperhero)
                .stream().map(mapper::toDtoForTop).collect(Collectors.toList());
    }

    @CachePut(value = "getTopSuperheroWithEnemies")
    @Override
    public List<SuperheroDtoForTop> getSuperheroesWithTheBiggestAmountsOfEnemies(Integer amountOfSuperhero) {
        return repository.getSuperheroesWithTheBiggestAmountsOfEnemies(amountOfSuperhero)
                .stream().map(mapper::toDtoForTop).collect(Collectors.toList());
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
    public SuperheroAwardsDto getSuperheroTop5Awards(Long id) {
        PageRequest page = PageRequest.of(0, 5, Sort.unsorted());
        Page<AwardView> responsePage = repository.getSuperheroAwards(id, page);
        List<AwardView> awards = responsePage.stream().collect(Collectors.toList());
        Superhero superhero = repository.getOne(id);
        return mapper.toDtoSuperheroAwards(superhero, awards);
    }

    @Override
    public Page<AwardView> getSuperheroAwards(Long id, Pageable pageable) {
        return repository.getSuperheroAwards(id, pageable);
    }
}

package torimia.superheroes.services;

import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.model.dto.SuperheroViewForTop;

import java.util.List;

public interface SuperheroService {

    SuperheroDto addNewFriend(Long superheroId, IdRequest id);

    SuperheroDto deleteFriend(Long superheroId, IdRequest id);

    SuperheroDto addEnemy(Long superheroId, IdRequest id);

    SuperheroDto deleteEnemy(Long superheroId, IdRequest id);

    List<SuperheroDto> findAll();

    SuperheroDto create(SuperheroDto superheroDTO);

    SuperheroDto update(Long superheroId, SuperheroDto updatedSuperheroDTO);

    void delete(Long superheroId);

    List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfFriends(Integer amountOfSuperhero);

    List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfEnemies(Integer amountOfSuperhero);

}

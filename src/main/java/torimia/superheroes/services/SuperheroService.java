package torimia.superheroes.services;

import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDTO;

import java.util.List;

public interface SuperheroService {

    SuperheroDTO addNewFriend(Long superheroId, IdRequest id);

    SuperheroDTO deleteFriend(Long superheroId, IdRequest id);

    SuperheroDTO addEnemy(Long superheroId, IdRequest id);

    SuperheroDTO deleteEnemy(Long superheroId, IdRequest id);

    List<SuperheroDTO> findAll();

    SuperheroDTO create(SuperheroDTO superheroDTO);

    SuperheroDTO update(Long superheroId, SuperheroDTO updatedSuperheroDTO);

    void delete(Long superheroId);

    List<SuperheroDTO> getSuperheroesWithTheBiggestAmountsOfFriends(Integer amountOfSuperhero);

    List<SuperheroDTO> getSuperheroesWithTheBiggestAmountsOfEnemies(Integer amountOfSuperhero);

}

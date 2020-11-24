package torimia.superheroes.services;

import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDTO;
import torimia.superheroes.model.entity.Superhero;

import java.util.List;

public interface SuperheroService {

    SuperheroDTO addNewFriend(Long superheroId, IdRequest id);

    SuperheroDTO deleteFriend(Long superheroId, IdRequest id);

    SuperheroDTO addEnemy(Long superheroId, IdRequest id);

    SuperheroDTO deleteEnemy(Long superheroId, IdRequest id);

    List<SuperheroDTO> findAll();

    SuperheroDTO save(SuperheroDTO superheroDTO);

    SuperheroDTO update (Long superheroId, SuperheroDTO updatedSuperheroDTO);

}

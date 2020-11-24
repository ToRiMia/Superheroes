package torimia.superheroes.services;

import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDTO;

public interface SuperheroService {

    SuperheroDTO addNewFriend(Long superheroId, IdRequest id);

    SuperheroDTO deleteFriend(Long superheroId, IdRequest id);

    SuperheroDTO addEnemy(Long superheroId, IdRequest id);

    SuperheroDTO deleteEnemy(Long superheroId, IdRequest id);

}

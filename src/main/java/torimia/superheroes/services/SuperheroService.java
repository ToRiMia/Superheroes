package torimia.superheroes.services;

import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDTO;

public interface SuperheroService {

    SuperheroDTO addNewFriend(Long superheroId, IdRequest friendId);

}

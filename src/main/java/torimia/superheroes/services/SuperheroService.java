package torimia.superheroes.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.model.dto.SuperheroViewForTop;

import java.util.List;

public interface SuperheroService {

    SuperheroDto addNewFriend(Long superheroId, IdRequest id);

    SuperheroDto deleteFriend(Long superheroId, IdRequest id);

    SuperheroDto addEnemy(Long superheroId, IdRequest id);

    SuperheroDto deleteEnemy(Long superheroId, IdRequest id);

    Page<SuperheroDto> getPage(Pageable page);

    SuperheroDto create(SuperheroDto superheroDTO);

    SuperheroDto update(Long superheroId, SuperheroDto updatedSuperheroDTO);

    void delete(Long superheroId);

    List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfFriends(Integer amountOfSuperhero);

    List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfEnemies(Integer amountOfSuperhero);

}

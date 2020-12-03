package torimia.superheroes.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import torimia.superheroes.model.dto.*;

import java.util.List;

public interface SuperheroService {

    SuperheroDto addNewFriend(Long superheroId, IdRequest id);

    SuperheroDto deleteFriend(Long superheroId, IdRequest id);

    SuperheroDto addEnemy(Long superheroId, IdRequest id);

    SuperheroDto deleteEnemy(Long superheroId, IdRequest id);

    Page<SuperheroDto> getPage(Pageable page);

    SuperheroDto create(SuperheroDto dto);

    SuperheroDto update(Long id, SuperheroDto dto);

    void delete(Long id);

    List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfFriends(Integer amountOfSuperhero);

    List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfEnemies(Integer amountOfSuperhero);

    SuperheroDto addAward(Long superheroId, IdRequest id);

    SuperheroDto deleteAward(Long superheroId, IdRequest id);

    List<AwardView> getSuperheroAwards(Long superheroId);

}

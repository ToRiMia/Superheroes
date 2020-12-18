package torimia.superheroes.superhero.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import torimia.superheroes.award.model.dto.AwardView;
import torimia.superheroes.superhero.model.dto.*;

import java.util.List;

public interface SuperheroService {

    Page<SuperheroDto> getPage(Pageable page);

    SuperheroDto getById(Long id);

    SuperheroDto create(SuperheroDto dto);

    SuperheroDto update(Long id, SuperheroDto dto);

    void delete(Long id);

    SuperheroDto addFriend(Long superheroId, IdRequest id);

    SuperheroDto deleteFriend(Long superheroId, IdRequest id);

    SuperheroDto addEnemy(Long superheroId, IdRequest id);

    SuperheroDto deleteEnemy(Long superheroId, IdRequest id);

    List<SuperheroDtoForTop> getSuperheroesWithTheBiggestAmountsOfFriends(Integer amountOfSuperhero);

    List<SuperheroDtoForTop> getSuperheroesWithTheBiggestAmountsOfEnemies(Integer amountOfSuperhero);

    SuperheroDto addAward(Long superheroId, IdRequest id);

    SuperheroDto deleteAward(Long superheroId, IdRequest id);

    SuperheroAwardsDto getSuperheroTop5Awards(Long superheroId);

    Page<AwardView> getSuperheroAwards(Long superheroId, Pageable pageable);
}


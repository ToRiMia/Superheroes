package torimia.superheroes.superhero;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import torimia.superheroes.award.model.dto.AwardView;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.superhero.model.dto.SuperheroViewForTop;

import java.util.List;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {

    @Query("select a from Award a where a.superhero.id = :superheroId order by a.rarity desc")
    Page<AwardView> getSuperheroAwards(Long superheroId, Pageable page);

    @Query(value = "SELECT superhero.id, superhero.nickname, superhero.first_name AS firstName, " +
            "superhero.last_name AS lastName, COUNT(superhero.id) AS amount " +
            "FROM superhero JOIN superhero_list_of_friends " +
            "ON (superhero.id = superhero_list_of_friends.superhero_id) " +
            "GROUP BY superhero.id " +
            "ORDER BY COUNT(superhero.id) DESC " +
            "LIMIT :amountOfSuperhero",
            nativeQuery = true)
    List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfFriends(Integer amountOfSuperhero);

    @Query(value = "SELECT superhero.id, superhero.nickname, superhero.first_name AS firstName, " +
            "superhero.last_name AS lastName, COUNT(superhero.id) AS amount  " +
            "FROM superhero JOIN superhero_list_of_enemies " +
            "ON (superhero.id = superhero_list_of_enemies.superhero_id) " +
            "GROUP BY superhero.id " +
            "ORDER BY COUNT(superhero.id) DESC " +
            "LIMIT :amountOfSuperhero",
            nativeQuery = true)
    List<SuperheroViewForTop> getSuperheroesWithTheBiggestAmountsOfEnemies(Integer amountOfSuperhero);

}

package torimia.superheroes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import torimia.superheroes.model.Superhero;

import java.util.List;

@Repository
public interface SuperheroRepo extends JpaRepository<Superhero, Long> {

    @Query(value = "SELECT superhero.id, superhero.name, superhero.first_name, " +
            "superhero.last_name, superhero.age, superhero.super_power " +
            "FROM superhero JOIN friend " +
            "ON (superhero.id = friend.superhero_id) " +
            "GROUP BY superhero.id " +
            "ORDER BY COUNT(superhero.id) DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Superhero> getFiveSuperheroesWithTheBiggestAmountsOfFriends();

    @Query(value = "SELECT superhero.id, superhero.name, superhero.first_name, " +
            "superhero.last_name, superhero.age, superhero.super_power " +
            "FROM superhero JOIN enemy " +
            "ON (superhero.id = enemy.superhero_id) " +
            "GROUP BY superhero.id " +
            "ORDER BY COUNT(superhero.id) DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Superhero> getFiveSuperheroesWithTheBiggestAmountsOfEnemies();

}

package torimia.superheroes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import torimia.superheroes.model.Enemy;
import torimia.superheroes.model.Superhero;

import java.util.List;

@Repository
public interface EnemyRepo extends JpaRepository<Enemy, Long>{

        List<Enemy> findAllBySuperheroId(Long superheroId);

        void deleteBySuperheroAndEnemyName(Superhero superhero, String EnemyName);
}

package torimia.superheroes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.Enemy;
import torimia.superheroes.model.Superhero;
import torimia.superheroes.repo.EnemyRepo;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("enemies")
public class EnemiesController {

        private final EnemyRepo enemyRepo;

        @Autowired
        public EnemiesController(EnemyRepo enemyRepo) {
            this.enemyRepo = enemyRepo;
        }

        @GetMapping("{id_superhero}")
        public List<Enemy> getMessages(@PathVariable("id_superhero") Long superheroId) {
            return enemyRepo.findAllBySuperheroId(superheroId);
        }

        @PostMapping("{id_superhero}")
        public Enemy addNewFriend(@PathVariable("id_superhero") Superhero superhero,
                                   @RequestBody Enemy enemy) {
            enemy.setSuperhero(superhero);
            return enemyRepo.save(enemy);
        }

        @Transactional
        @DeleteMapping("{id}/{enemyName}")
        public void remove(@PathVariable("id") Superhero superhero,
                           @PathVariable("enemyName") String enemyName) {
            enemyRepo.deleteBySuperheroAndEnemyName(superhero, enemyName);
        }
}

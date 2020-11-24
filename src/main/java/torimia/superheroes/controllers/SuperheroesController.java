package torimia.superheroes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDTO;
import torimia.superheroes.model.entity.Superhero;
import torimia.superheroes.repo.SuperheroRepo;
import torimia.superheroes.services.SuperheroService;

import java.util.List;

@RestController
@RequestMapping("superhero")
@RequiredArgsConstructor
public class SuperheroesController {

    private final SuperheroRepo superheroRepo; // change
    private final SuperheroService service;

    @GetMapping
    public List<SuperheroDTO> getAll() {
        return service.findAll();
    }

    @PostMapping
    public SuperheroDTO create(@RequestBody Superhero newSuperhero) {
        return service.save(newSuperhero);
    }

    @PostMapping("add_friend/{id}")
    public SuperheroDTO addNewFriend(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.addNewFriend(superheroId, id);
    }

    @DeleteMapping("delete_friend/{id}")
    public SuperheroDTO deleteFriend(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.deleteFriend(superheroId, id);
    }

    @PostMapping("add_enemy/{id}")
    public SuperheroDTO addEnemy(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.addEnemy(superheroId, id);
    }

    @DeleteMapping("delete_enemy/{id}")
    public SuperheroDTO deleteEnemy(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.deleteEnemy(superheroId, id);
    }

    @PutMapping("{id}")
    public Superhero update(
            @PathVariable("id") Superhero superheroFromDB,
            @RequestBody Superhero superhero) {
        BeanUtils.copyProperties(superhero, superheroFromDB, "id");
        return superheroRepo.save(superheroFromDB);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") Superhero superhero) {
        superheroRepo.delete(superhero);
    }

    @GetMapping("top5_friends")
    public List<Superhero> getTop5SuperheroWithFriends() {
        return superheroRepo.getFiveSuperheroesWithTheBiggestAmountsOfFriends();
    }

    @GetMapping("top5_enemies")
    public List<Superhero> getTop5SuperheroWithEnemies() {
        return superheroRepo.getFiveSuperheroesWithTheBiggestAmountsOfEnemies();
    }

/*
    {
        "name":"Batman",
            "firstName":"Bruce",
            "lastName":"Wayne",
            "age":48,
            "superPower":"The brutal voice"
    }
*/
}


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

    private final SuperheroRepo superheroRepo;
    private final SuperheroService service;

    @GetMapping
    public List<Superhero> getMessages() {
        return superheroRepo.findAll();
    }

    @PostMapping
    public Superhero create(@RequestBody Superhero newSuperhero) {
        return superheroRepo.save(newSuperhero);
    }

    @PostMapping("add_friend/{id}")
    public SuperheroDTO addFriend(@PathVariable("id") Long superheroId, @RequestBody IdRequest friendId) {
        return service.addNewFriend(superheroId, friendId);
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


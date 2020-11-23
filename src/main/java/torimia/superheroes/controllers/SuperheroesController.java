package torimia.superheroes.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.Superhero;
import torimia.superheroes.repo.SuperheroRepo;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("superhero")
public class SuperheroesController {

    private final SuperheroRepo superheroRepo;

    public SuperheroesController(SuperheroRepo superheroRepo) {
        this.superheroRepo = superheroRepo;
    }

    @GetMapping
    public List<Superhero> getMessages() {
        return superheroRepo.findAll();
    }

    @PostMapping
    public Superhero create(@RequestBody Superhero newSuperhero) {
        return superheroRepo.save(newSuperhero);
    }

    @PostMapping("add_friend/{id}")
    public Superhero addNewFriend(@PathVariable("id") Superhero superhero,
                                  @RequestBody Long friendId) {
        List<Superhero> friends = superhero.getListOfFriends();
        Superhero friend = superheroRepo.getOne(friendId);
        friends.add(friend);
        superhero.setListOfFriends(friends);
        return superhero;
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


package torimia.superheroes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.model.dto.SuperheroViewForTop;
import torimia.superheroes.services.SuperheroService;
import java.util.List;

@RestController
@RequestMapping("superhero")
@RequiredArgsConstructor
public class SuperheroesController {

    private final SuperheroService service;

    @GetMapping
    public List<SuperheroDto> getAll() {
        return service.findAll();
    }

    @PostMapping
    public SuperheroDto create(@RequestBody SuperheroDto newSuperheroDTO) {
        return service.create(newSuperheroDTO);
    }

    @PutMapping("{id}")
    public SuperheroDto update(
            @PathVariable("id") Long superheroId,
            @RequestBody SuperheroDto updatedSuperheroDTO) {
        return service.update(superheroId, updatedSuperheroDTO);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") Long superheroId) {
        service.delete(superheroId);
    }

    @PatchMapping("add_friend/{id}")
    public SuperheroDto addNewFriend(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.addNewFriend(superheroId, id);
    }

    @DeleteMapping("delete_friend/{id}")
    public SuperheroDto deleteFriend(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.deleteFriend(superheroId, id);
    }

    @PatchMapping("add_enemy/{id}")
    public SuperheroDto addEnemy(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.addEnemy(superheroId, id);
    }

    @DeleteMapping("delete_enemy/{id}")
    public SuperheroDto deleteEnemy(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.deleteEnemy(superheroId, id);
    }

    @GetMapping("top_friends")
    public List<SuperheroViewForTop> getTopSuperheroWithFriends(@RequestParam(value = "amount", defaultValue = "5", required = false) Integer amountOfSuperhero) {
        return service.getSuperheroesWithTheBiggestAmountsOfFriends(amountOfSuperhero);
    }

    @GetMapping("top_enemies")
    public List<SuperheroViewForTop> getTop5SuperheroWithEnemies(@RequestParam(value = "amount", defaultValue = "5", required = false) Integer amountOfSuperhero) {
        return service.getSuperheroesWithTheBiggestAmountsOfEnemies(amountOfSuperhero);
    }

/*
{
    "name":"Batman",
    "firstName":"Bruce",
    "lastName":"Wayne",
    "age":48,
    "superPower":"The brutal voice"
}

post
{
    "name": "Batman",
    "firstName": "Ka",
    "lastName": "Oi",
    "age": 25,
    "superPower": "The brutal voice",
    "listOfFriendsId": [3,2],
    "listOfEnemiesId": [1]
}
*/
}


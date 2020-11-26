package torimia.superheroes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDTO;
import torimia.superheroes.services.SuperheroService;
import java.util.List;

@RestController
@RequestMapping("superhero")
@RequiredArgsConstructor
public class SuperheroesController {

    private final SuperheroService service;

    @GetMapping
    public List<SuperheroDTO> getAll() {
        return service.findAll();
    }

    @PostMapping
    public SuperheroDTO create(@RequestBody SuperheroDTO newSuperheroDTO) {
        return service.create(newSuperheroDTO);
    }

    @PutMapping("{id}")
    public SuperheroDTO update(
            @PathVariable("id") Long superheroId,
            @RequestBody SuperheroDTO updatedSuperheroDTO) {
        return service.update(superheroId, updatedSuperheroDTO);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") Long superheroId) {
        service.delete(superheroId);
    }

    @PatchMapping("add_friend/{id}")
    public SuperheroDTO addNewFriend(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.addNewFriend(superheroId, id);
    }

    @DeleteMapping("delete_friend/{id}")
    public SuperheroDTO deleteFriend(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.deleteFriend(superheroId, id);
    }

    @PatchMapping("add_enemy/{id}")
    public SuperheroDTO addEnemy(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.addEnemy(superheroId, id);
    }

    @DeleteMapping("delete_enemy/{id}")
    public SuperheroDTO deleteEnemy(@PathVariable("id") Long superheroId, @RequestBody IdRequest id) {
        return service.deleteEnemy(superheroId, id);
    }

    @GetMapping("top_friends")
    public List<SuperheroDTO> getTopSuperheroWithFriends(@RequestParam(value = "amount", defaultValue = "5", required = false) Integer amountOfSuperhero) {
        return service.getSuperheroesWithTheBiggestAmountsOfFriends(amountOfSuperhero);
    }

    @GetMapping("top_enemies")
    public List<SuperheroDTO> getTop5SuperheroWithEnemies(@RequestParam(value = "amount", defaultValue = "5", required = false) Integer amountOfSuperhero) {
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


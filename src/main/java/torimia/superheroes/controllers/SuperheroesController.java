package torimia.superheroes.controllers;

import lombok.RequiredArgsConstructor;
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
    public SuperheroDTO create(@RequestBody SuperheroDTO newSuperheroDTO) {
        return service.save(newSuperheroDTO);
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


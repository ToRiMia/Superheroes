package torimia.superheroes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.dto.*;
import torimia.superheroes.services.SuperheroService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("superhero")
@RequiredArgsConstructor
public class SuperheroesController {

    private final SuperheroService service;

    @GetMapping
    public Page<SuperheroDto> getAllPage(@SortDefault(sort = "id") Pageable page) {
        return service.getPage(page);
    }

    @GetMapping("{id}")
    public SuperheroDto getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @PostMapping
    public SuperheroDto create(@Valid @RequestBody SuperheroDto dto) {
        return service.create(dto);
    }

    @PutMapping("{id}")
    public SuperheroDto update(
            @PathVariable("id") Long id,
            @Valid @RequestBody SuperheroDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @PatchMapping("add_friend/{id}")
    public SuperheroDto addNewFriend(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.addNewFriend(superheroId, id);
    }

    @DeleteMapping("delete_friend/{id}")
    public SuperheroDto deleteFriend(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.deleteFriend(superheroId, id);
    }

    @PatchMapping("add_enemy/{id}")
    public SuperheroDto addEnemy(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.addEnemy(superheroId, id);
    }

    @DeleteMapping("delete_enemy/{id}")
    public SuperheroDto deleteEnemy(@PathVariable("id") Long superheroId, @Valid  @RequestBody IdRequest id) {
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

    @PatchMapping("add_award/{id}")
    public SuperheroDto addAward(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.addAward(superheroId, id);
    }

    @DeleteMapping("delete_award/{id}")
    public SuperheroDto deleteAward(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.deleteAward(superheroId, id);
    }

    @GetMapping("awards/{id}")
    public List<AwardView> getSuperheroAwards(@PathVariable("id") Long superheroId) {
        return service.getSuperheroAwards(superheroId);
    }

/*
{
    "nickname":"Batman",
    "firstName":"Bruce",
    "lastName":"Wayne",
    "age":48,
    "superPower":"The brutal voice"
}

post
{
    "nickname": "Batman",
    "firstName": "Ka",
    "lastName": "Oi",
    "age": 25,
    "superPower": "The brutal voice",
    "listOfFriendsId": [3,2],
    "listOfEnemiesId": [1]
}
*/
}


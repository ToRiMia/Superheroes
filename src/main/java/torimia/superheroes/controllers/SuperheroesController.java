package torimia.superheroes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.dto.*;
import torimia.superheroes.services.SuperheroService;

import javax.validation.Valid;
import java.util.List;

import static torimia.superheroes.controllers.SuperheroesController.Path.*;
import static torimia.superheroes.controllers.SuperheroesController.Path.Variable.ID;

@RestController
@RequestMapping("superhero")
@RequiredArgsConstructor
public class SuperheroesController {

    private final SuperheroService service;

    interface Path {

        String BASE_URL = "/superhero";

        String BY_ID = "/{" + ID + "}";

        String ADD_FRIEND = "/add_friend";

        String DELETE_FRIEND = "/delete_friend";

        String ADD_ENEMY = "/add_enemy";

        String DELETE_ENEMY = "/delete_enemy";

        String ADD_AWARD = "/add_award";

        String DELETE_AWARD = "/delete_award";

        interface Variable {
            String ID = "id";
        }
    } // TODO: 14.12.20 переправити урли

    @GetMapping
    public Page<SuperheroDto> getAllPage(@SortDefault(sort = "id") Pageable page) {
        return service.getPage(page);
    }

    @GetMapping(BY_ID)
    public SuperheroDto getById(@PathVariable(ID) Long id) {
        return service.getById(id);
    }

    @PostMapping
    public SuperheroDto create(@Valid @RequestBody SuperheroDto dto) {
        return service.create(dto);
    }

    @PutMapping(BY_ID)
    public SuperheroDto update(
            @PathVariable(ID) Long id,
            @Valid @RequestBody SuperheroDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping(BY_ID)
    public void remove(@PathVariable(ID) Long id) {
        service.delete(id);
    }

    @PatchMapping(ADD_FRIEND + BY_ID)
    public SuperheroDto addFriend(@PathVariable(ID) Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.addFriend(superheroId, id);
    }

    @DeleteMapping(DELETE_FRIEND + BY_ID)
    public SuperheroDto deleteFriend(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.deleteFriend(superheroId, id);
    }

    @PatchMapping(ADD_ENEMY + BY_ID)
    public SuperheroDto addEnemy(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.addEnemy(superheroId, id);
    }

    @DeleteMapping(DELETE_ENEMY + BY_ID)
    public SuperheroDto deleteEnemy(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
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

    @PatchMapping(ADD_AWARD + BY_ID)
    public SuperheroDto addAward(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.addAward(superheroId, id);
    }

    @DeleteMapping(DELETE_AWARD + BY_ID)
    public SuperheroDto deleteAward(@PathVariable("id") Long superheroId, @Valid @RequestBody IdRequest id) {
        return service.deleteAward(superheroId, id);
    }

    @GetMapping("awards_top_5/{id}")
    public SuperheroAwardsDto getSuperheroTop5Awards(@PathVariable("id") Long superheroId) {
        return service.getSuperheroTop5Awards(superheroId);
    }

    @GetMapping("awards/{id}")
    public Page<AwardView> getSuperheroAwards(@PathVariable("id") Long superheroId, Pageable page) {
        return service.getSuperheroAwards(superheroId, page);
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


package torimia.superheroes.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDTO;
import torimia.superheroes.model.entity.Superhero;
import torimia.superheroes.repo.SuperheroRepo;

import java.util.List;
import java.util.stream.Collectors;

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

//    ResponseEntity<?>
    @PostMapping("add_friend/{id}")
    public SuperheroDTO addNewFriend(@PathVariable("id") Long superheroId,
                                               @RequestBody IdRequest friendId) {

        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfFriends = superhero.getListOfFriends();
        Superhero friend = superheroRepo.getOne(friendId.getFriendId());
        listOfFriends.add(friend);

        return superheroToDTO(superheroRepo.save(superhero));
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

    private SuperheroDTO superheroToDTO(Superhero superhero){
        SuperheroDTO superheroDTO = new SuperheroDTO();
        superheroDTO.setId(superhero.getId());
        superheroDTO.setName(superhero.getName());
        superheroDTO.setFirstName(superhero.getFirstName());
        superheroDTO.setLastName(superhero.getLastName());
        superheroDTO.setAge(superhero.getAge());
        superheroDTO.setSuperPower(superhero.getSuperPower());
        superheroDTO.setListOfFriendsId(superhero.getListOfFriends().stream().map(Superhero::getId).collect(Collectors.toList()));
        return superheroDTO;
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


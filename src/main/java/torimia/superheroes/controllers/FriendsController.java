package torimia.superheroes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import torimia.superheroes.model.Friend;
import torimia.superheroes.model.Superhero;
import torimia.superheroes.repo.FriendRepo;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("friends")
public class FriendsController {

    private final FriendRepo friendRepo;

    @Autowired
    public FriendsController(FriendRepo friendRepo) {
        this.friendRepo = friendRepo;
    }

    @GetMapping("{id_superhero}")
    public List<Friend> getMessages(@PathVariable("id_superhero") Long superheroId) {
        return friendRepo.findAllBySuperheroId(superheroId);
    }

    @PostMapping("{id_superhero}")
    public Friend addNewFriend(@PathVariable("id_superhero") Superhero superhero,
                               @RequestBody Friend friend) {
        friend.setSuperhero(superhero);
        return friendRepo.save(friend);
    }

    @Transactional
    @DeleteMapping("{id}/{friendName}")
    public void remove(@PathVariable("id") Superhero superhero,
                       @PathVariable("friendName") String friendName) {
        friendRepo.deleteBySuperheroAndFriendName(superhero, friendName);
    }
    /*
    {
        "friendName":"Superman"
    }
    */

}

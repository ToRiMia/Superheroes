package torimia.superheroes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDTO;
import torimia.superheroes.model.entity.Superhero;
import torimia.superheroes.repo.SuperheroRepo;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SuperheroServiceInput implements SuperheroService{

    private final SuperheroRepo superheroRepo;

    @Override
    public SuperheroDTO addNewFriend(Long superheroId, IdRequest friendId) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfFriends = superhero.getListOfFriends();
        Superhero friend = superheroRepo.getOne(friendId.getFriendId());
        listOfFriends.add(friend);
        return superheroToDTO(superheroRepo.save(superhero));
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
}

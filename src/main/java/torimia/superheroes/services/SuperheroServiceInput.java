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
    public SuperheroDTO addNewFriend(Long superheroId, IdRequest id) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfFriends = superhero.getListOfFriends();
        Superhero friend = superheroRepo.getOne(id.getId());
        listOfFriends.add(friend);
        return superheroToDTO(superheroRepo.save(superhero));
    }

    @Override
    public SuperheroDTO deleteFriend(Long superheroId, IdRequest id) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfFriends = superhero.getListOfFriends();
        Superhero friend = superheroRepo.getOne(id.getId());
        listOfFriends.remove(friend);
        return superheroToDTO(superheroRepo.save(superhero));
    }

    @Override
    public SuperheroDTO addEnemy(Long superheroId, IdRequest id) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfEnemies = superhero.getListOfEnemies();
        Superhero enemy = superheroRepo.getOne(id.getId());
        listOfEnemies.add(enemy);
        return superheroToDTO(superheroRepo.save(superhero));
    }

    @Override
    public SuperheroDTO deleteEnemy(Long superheroId, IdRequest id) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfEnemies = superhero.getListOfEnemies();
        Superhero enemy = superheroRepo.getOne(id.getId());
        listOfEnemies.remove(enemy);
        return superheroToDTO(superheroRepo.save(superhero));
    }

    //Треба винести його в маппер
    private SuperheroDTO superheroToDTO(Superhero superhero){
        SuperheroDTO superheroDTO = new SuperheroDTO();
        superheroDTO.setId(superhero.getId());
        superheroDTO.setName(superhero.getName());
        superheroDTO.setFirstName(superhero.getFirstName());
        superheroDTO.setLastName(superhero.getLastName());
        superheroDTO.setAge(superhero.getAge());
        superheroDTO.setSuperPower(superhero.getSuperPower());
        superheroDTO.setListOfFriendsId(superhero.getListOfFriends().stream().map(Superhero::getId).collect(Collectors.toList()));
        superheroDTO.setListOfEnemiesId(superhero.getListOfEnemies().stream().map(Superhero::getId).collect(Collectors.toList()));
        return superheroDTO;
    }
}

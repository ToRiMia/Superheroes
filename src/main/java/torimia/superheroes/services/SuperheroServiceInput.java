package torimia.superheroes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import torimia.superheroes.model.dto.IdRequest;
import torimia.superheroes.model.dto.SuperheroDTO;
import torimia.superheroes.model.entity.Superhero;
import torimia.superheroes.repo.SuperheroRepository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SuperheroServiceInput implements SuperheroService {

    private final SuperheroRepository superheroRepo;

    @Override
    public List<SuperheroDTO> findAll() {
        List<Superhero> superheroes = superheroRepo.findAll();
        return superheroes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public SuperheroDTO create(SuperheroDTO superheroDTO) {
        Superhero superhero = toEntity(superheroDTO);
        superhero.setId(null);
        return toDTO(superheroRepo.save(superhero));
    }

    @Override
    public SuperheroDTO update(Long superheroId, SuperheroDTO updatedSuperheroDTO) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        toEntityUpdate(updatedSuperheroDTO, superhero);
        return toDTO(superheroRepo.save(superhero));
    }

    @Override
    public void delete(Long superheroId) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        superheroRepo.delete(superhero);
    }

    @Override
    public SuperheroDTO addNewFriend(Long superheroId, IdRequest id) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfFriends = superhero.getListOfFriends();
        Superhero friend = superheroRepo.getOne(id.getId());
        listOfFriends.add(friend);
        return toDTO(superheroRepo.save(superhero));
    }

    @Override
    public SuperheroDTO deleteFriend(Long superheroId, IdRequest id) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfFriends = superhero.getListOfFriends();
        Superhero friend = superheroRepo.getOne(id.getId());
        listOfFriends.remove(friend);
        return toDTO(superheroRepo.save(superhero));
    }

    @Override
    public SuperheroDTO addEnemy(Long superheroId, IdRequest id) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfEnemies = superhero.getListOfEnemies();
        Superhero enemy = superheroRepo.getOne(id.getId());
        listOfEnemies.add(enemy);
        return toDTO(superheroRepo.save(superhero));
    }

    @Override
    public SuperheroDTO deleteEnemy(Long superheroId, IdRequest id) {
        Superhero superhero = superheroRepo.getOne(superheroId);
        List<Superhero> listOfEnemies = superhero.getListOfEnemies();
        Superhero enemy = superheroRepo.getOne(id.getId());
        listOfEnemies.remove(enemy);
        return toDTO(superheroRepo.save(superhero));
    }

    @Override
    public List<SuperheroDTO> getSuperheroesWithTheBiggestAmountsOfFriends(Integer amountOfSuperhero) {
        List<Superhero> superheroes = superheroRepo.getSuperheroesWithTheBiggestAmountsOfFriends(amountOfSuperhero);
        return superheroes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SuperheroDTO> getSuperheroesWithTheBiggestAmountsOfEnemies(Integer amountOfSuperhero) {
        List<Superhero> superheroes = superheroRepo.getSuperheroesWithTheBiggestAmountsOfEnemies(amountOfSuperhero);
        return superheroes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    //Треба винести його в маппер
    private SuperheroDTO toDTO(Superhero superhero) {
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

    private Superhero toEntity(SuperheroDTO superheroDTO) {
        Superhero superhero = new Superhero();
        superhero.setId(superheroDTO.getId());
        superhero.setName(superheroDTO.getName());
        superhero.setFirstName(superheroDTO.getFirstName());
        superhero.setLastName(superheroDTO.getLastName());
        superhero.setAge(superheroDTO.getAge());
        superhero.setSuperPower(superheroDTO.getSuperPower());
        superhero.setListOfFriends(superheroDTO.getListOfFriendsId().stream().map(superheroRepo::getOne).collect(Collectors.toList()));
        superhero.setListOfEnemies(superheroDTO.getListOfEnemiesId().stream().map(superheroRepo::getOne).collect(Collectors.toList()));
        return superhero;
    }

    private void toEntityUpdate(SuperheroDTO superheroDTO, Superhero superhero) {
        superhero.setName(superheroDTO.getName());
        superhero.setFirstName(superheroDTO.getFirstName());
        superhero.setLastName(superheroDTO.getLastName());
        superhero.setAge(superheroDTO.getAge());
        superhero.setSuperPower(superheroDTO.getSuperPower());
        superhero.setListOfFriends(superheroDTO.getListOfFriendsId().stream().map(superheroRepo::getOne).collect(Collectors.toList()));
        superhero.setListOfEnemies(superheroDTO.getListOfEnemiesId().stream().map(superheroRepo::getOne).collect(Collectors.toList()));
    }
}

package torimia.superheroes.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class SuperheroDTO {

    private Long id;

    private String name;

    private String firstName;

    private String lastName;

    private int age;

    private String superPower;

    private List<Long> listOfFriendsId = new ArrayList<>();

    private List<Long> listOfEnemiesId = new ArrayList<>();

    public void setListOfFriendsId(List<Long> listOfFriendsId) {
        this.listOfFriendsId = Objects.requireNonNullElseGet(listOfFriendsId, ArrayList::new);
    }

    public void setListOfEnemiesId(List<Long> listOfEnemiesId) {
        this.listOfEnemiesId = Objects.requireNonNullElseGet(listOfEnemiesId, ArrayList::new);
    }
}

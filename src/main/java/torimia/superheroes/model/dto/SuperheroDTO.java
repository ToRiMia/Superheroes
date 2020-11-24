package torimia.superheroes.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SuperheroDTO {

    private Long id;

    private String name;

    private String firstName;

    private String lastName;

    private int age;

    private String superPower;

    private List<Long> listOfFriendsId;

    private List<Long> listOfEnemiesId;
}

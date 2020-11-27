package torimia.superheroes.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class SuperheroDto {

    private Long id;

    private String name;

    private String firstName;

    private String lastName;

    private int age;

    private String superPower;

    private List<Long> listOfFriendsId = new ArrayList<>();

    private List<Long> listOfEnemiesId = new ArrayList<>();
}

package torimia.superheroes.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class SuperheroDto {

    private Long id;

    @Size(min = 2, message = "Nickname cannot be less than 1 letter!")
    @NotNull(message = "Name cannot be empty!")
    private String nickname;

    @Size(min = 2, message = "First name cannot be less than 1 letter!")
    @NotNull(message = "First name cannot be empty!")
    private String firstName;

    @Size(min = 2, message = "Last name cannot be less than 1 letter!")
    @NotNull(message = "Last name cannot be empty!")
    private String lastName;

    @Min(value = 1, message = "Age cannot be less than 1 year!")
    private int age;

    @NotNull(message = "Id cannot be empty!")
    @Size(min = 3, message = "Super power cannot be less than 3 letters!")
    @Size(max = 1000, message = "Super power cannot be more than 1000 letters!")
    private String superPower;

    private List<Long> listOfFriendsId = new ArrayList<>();

    private List<Long> listOfEnemiesId = new ArrayList<>();
}

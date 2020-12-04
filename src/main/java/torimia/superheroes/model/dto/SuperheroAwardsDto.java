package torimia.superheroes.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SuperheroAwardsDto {

    private Long id;

    private String nickname;

    private String firstName;

    private String lastName;

    private int age;

    private String superPower;

    private List<Long> listOfFriendsId;

    private List<Long> listOfEnemiesId;

    private List<AwardView> awards;
}

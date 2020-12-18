package torimia.superheroes.superhero.model.dto;

import lombok.Data;
import torimia.superheroes.award.model.dto.AwardDto;

import java.util.List;

@Data
public class SuperheroAwardsDto {

    private Long id;

    private String nickname;

    private String firstName;

    private String lastName;

    private int age;

    private String superPower;

    private Integer damage;

    private Integer health;

    private List<Long> listOfFriendsId;

    private List<Long> listOfEnemiesId;

    private List<AwardDto> awards;
}

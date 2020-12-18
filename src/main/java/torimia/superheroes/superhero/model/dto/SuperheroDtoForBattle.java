package torimia.superheroes.superhero.model.dto;

import lombok.Data;

@Data
public class SuperheroDtoForBattle {

    private Long id;

    private String nickname;

    private Integer damage;

    private Integer health;

}

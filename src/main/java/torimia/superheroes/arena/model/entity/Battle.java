package torimia.superheroes.arena.model.entity;

import lombok.Data;
import torimia.superheroes.superhero.model.dto.*;

@Data
public class Battle {

    SuperheroDtoForBattle superhero1;
    SuperheroDtoForBattle superhero2;
}

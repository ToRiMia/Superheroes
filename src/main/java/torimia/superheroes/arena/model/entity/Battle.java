package torimia.superheroes.arena.model.entity;

import lombok.*;
import torimia.superheroes.superhero.model.dto.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Battle {

    private Long id;

    private SuperheroDtoForBattle superhero1;

    private SuperheroDtoForBattle superhero2;
}

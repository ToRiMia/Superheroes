package torimia.superheroes.arena.model.dto;

import lombok.*;
import torimia.superheroes.superhero.model.dto.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleDtoForServer {

    private Long id;

    private SuperheroDtoForBattle superhero1;

    private SuperheroDtoForBattle superhero2;
}

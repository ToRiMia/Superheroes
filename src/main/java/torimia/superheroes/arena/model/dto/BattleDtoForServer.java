package torimia.superheroes.arena.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import torimia.superheroes.superhero.model.dto.SuperheroDtoForBattle;

import javax.validation.Valid;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleDtoForServer {

    private Long id;

    @Valid
    private List<SuperheroDtoForBattle> superheroes;
}

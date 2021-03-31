package torimia.superheroes.arena.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleDtoResultFromServerDto implements BattleDtoResultFromServerView {

    @NotNull
    private Long id;

    private Long winnerId;

    @NotNull
    private Integer attackNumber;

    @NotNull
    private Instant startOfBattle;

    @NotNull
    private Instant endOfBattle;
}

package torimia.superheroes.arena.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
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

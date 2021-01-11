package torimia.superheroes.arena.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleDtoResultFromServerDto implements BattleDtoResultFromServerView {

    @NotNull
    private Long id;

  //  @NotNull
    private Long winnerId;

    @NotNull
    private Long battleTime;

    @NotNull
    private Integer attackNumber;

    @NotNull
    private Date date;
}

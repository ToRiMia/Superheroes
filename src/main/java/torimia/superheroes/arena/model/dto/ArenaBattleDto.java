package torimia.superheroes.arena.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArenaBattleDto {

    @NotNull
    Long winnerId;

    @NotNull
    Long loserId;

    @NotNull
    Long battleTime;

    @NotNull
    Integer attackNumber;

    @NotNull
    Date date;

}

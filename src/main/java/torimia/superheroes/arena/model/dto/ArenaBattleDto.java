package torimia.superheroes.arena.model.dto;

import lombok.*;
import torimia.superheroes.enums.ValueOfEnum;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArenaBattleDto {

    @NotNull
    private Long id;

    @NotNull
    private Long winnerId;

    @NotNull
    private Long loserId;

    @NotNull
    private Long battleTime;

    @NotNull
    private Integer attackNumber;

    @NotNull
    private Date date;
}

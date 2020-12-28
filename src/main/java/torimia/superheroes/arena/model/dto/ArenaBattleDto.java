package torimia.superheroes.arena.model.dto;

import lombok.*;
import torimia.superheroes.arena.model.enums.FightStatus;
import torimia.superheroes.award.model.entity.Rarity;
import torimia.superheroes.award.model.enums.ValueOfEnum;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArenaBattleDto {

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

    @NotNull
    @ValueOfEnum(enumClass = FightStatus.class)
    private FightStatus status;

}

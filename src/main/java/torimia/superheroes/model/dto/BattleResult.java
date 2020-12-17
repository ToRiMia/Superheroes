package torimia.superheroes.model.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleResult {

    String nameWinner;

    String nameLoser;

    Time battleTime;

    Integer attackNumber;

    Date date;

}

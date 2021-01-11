package torimia.superheroes.arena.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleStatusDto {
   private Long id;

   private BattleStatus message;
}

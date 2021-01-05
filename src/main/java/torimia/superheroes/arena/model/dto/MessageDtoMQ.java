package torimia.superheroes.arena.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDtoMQ {
   private Long id;

   private FightStatus message;
}

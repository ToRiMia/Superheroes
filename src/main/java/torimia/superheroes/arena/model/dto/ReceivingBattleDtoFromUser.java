package torimia.superheroes.arena.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class ReceivingBattleDtoFromUser {

    @NotNull(message = "Id cannot be null!")
    @Size(min = 2, message = "Cannot be less than 2 fighters!")
    Set<@Min(value = 1, message = "Id cannot be less than 1!") Long> fightersIds;
}

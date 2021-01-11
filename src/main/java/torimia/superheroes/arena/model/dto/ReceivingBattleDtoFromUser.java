package torimia.superheroes.arena.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReceivingBattleDtoFromUser {

    @NotNull(message = "Id cannot be null!")
//    @Min(value = 1, message = "Id cannot be less than 1!")
    List<Long> fightersIds;
}

package torimia.superheroes.arena.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ReceivingBattleDtoFromUser {

    @NotNull(message = "Id first fighter cannot be null!")
    @Min(value = 1, message = "Id first fighter cannot be less than 1!")
    Long firstFighterId;

    @NotNull(message = "Id second fighter cannot be null!")
    @Min(value = 1, message = "Id second fighter cannot be less than 1!")
    Long secondFighterId;
}

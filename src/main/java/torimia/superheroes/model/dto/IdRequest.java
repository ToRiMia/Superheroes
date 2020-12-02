package torimia.superheroes.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class IdRequest {

    @NotNull(message = "Id cannot be null!")
    @Min(value = 1, message = "Id cannot be less than 1!")
    private Long id;

}

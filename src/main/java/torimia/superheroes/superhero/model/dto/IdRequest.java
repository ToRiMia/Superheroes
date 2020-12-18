package torimia.superheroes.superhero.model.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IdRequest {

    @NotNull(message = "Id cannot be null!")
    @Min(value = 1, message = "Id cannot be less than 1!")
    private Long id;

    public static IdRequest of(Long id) {
        return new IdRequest(id);
    }

}

package torimia.superheroes.model.dto;

import lombok.Data;
import torimia.superheroes.model.entity.Rarity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AwardDto implements AwardView{

    private Long id;

    @NotNull(message = "Name cannot be empty!")
    @Size(min = 2, message = "Name cannot be less than 1 letter!")
    private String name;

    @NotNull(message = "Rarity must be COMMON, RARE or LEGENDARY!")
    private Rarity rarity;
}


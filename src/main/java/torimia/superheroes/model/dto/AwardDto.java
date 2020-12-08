package torimia.superheroes.model.dto;

import lombok.Data;
import torimia.superheroes.model.entity.Rarity;
import torimia.superheroes.model.enums.ValueOfEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Locale;

@Data
public class AwardDto implements AwardView {

    private Long id;

    @NotNull(message = "Name cannot be empty!")
    @Size(min = 2, message = "Name cannot be less than 1 letter!")
    private String name;

    @NotNull(message = "Rarity must be not null!")
    @ValueOfEnum(enumClass = Rarity.class)
    private String rarity;

    public void setRarity(String rarity) {
        if (rarity == null)
            this.rarity = null;
        else
            this.rarity = rarity.toUpperCase(Locale.ROOT);
    }
}


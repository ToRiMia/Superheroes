package torimia.superheroes.award.model.dto;

import lombok.*;
import torimia.superheroes.award.model.entity.Rarity;
import torimia.superheroes.enums.ValueOfEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AwardDto implements AwardView {

    private Long id;

    @NotNull(message = "Name cannot be empty!")
    @Size(min = 2, message = "Name cannot be less than 1 letter!")
    private String name;

    @NotNull(message = "Rarity must be not null!")
    @ValueOfEnum(enumClass = Rarity.class, message = "Rarity must be COMMON, RARE or LEGENDARY")
    private String rarity;

    public void setRarity(String rarity) {
        if (rarity == null)
            this.rarity = null;
        else
            this.rarity = rarity.toUpperCase(Locale.ROOT);
    }
}


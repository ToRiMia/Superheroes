package torimia.superheroes.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Rarity {

    COMMON(0), RARE(1), LEGENDARY(2);

    private final int value;

    Rarity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Rarity forValues(@JsonProperty("rarity") String rarity){
        return Rarity.valueOf(rarity);
    }


}

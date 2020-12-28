package torimia.superheroes.arena.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum FightStatus {

    STARTED(0), FINISHED_SUCCESSFUL(1), FINISHED_UNSUCCESSFUL(2);

    private final int value;

    FightStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static FightStatus forValues(@JsonProperty("fightStatus") String fightStatus){
        return FightStatus.valueOf(fightStatus);
    }
}

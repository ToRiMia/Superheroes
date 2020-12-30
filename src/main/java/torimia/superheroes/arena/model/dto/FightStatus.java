package torimia.superheroes.arena.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum FightStatus {

    NOT_STARTED(0),  STARTED(1), FINISHED_SUCCESSFUL(2), FINISHED_UNSUCCESSFUL(3);

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

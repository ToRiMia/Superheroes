package torimia.superheroes.arena.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum BattleStatus {

    NOT_STARTED(0),  STARTED(1), FINISHED_SUCCESSFUL(2), FINISHED_UNSUCCESSFUL(3);

    private final int value;

    BattleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static BattleStatus forValues(@JsonProperty("battleStatus") String battleStatus){
        return BattleStatus.valueOf(battleStatus);
    }
}

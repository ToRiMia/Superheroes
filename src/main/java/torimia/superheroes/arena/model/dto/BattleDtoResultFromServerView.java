package torimia.superheroes.arena.model.dto;

import java.time.Instant;

public interface BattleDtoResultFromServerView {

    Long getId();

    Long getWinnerId();

    Integer getAttackNumber();

    Instant getStartOfBattle();

    Instant getEndOfBattle();

}

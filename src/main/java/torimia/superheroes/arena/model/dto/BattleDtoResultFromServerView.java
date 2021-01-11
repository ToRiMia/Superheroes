package torimia.superheroes.arena.model.dto;

import java.sql.Date;

public interface BattleDtoResultFromServerView {

    Long getId();

    Long getWinnerId();

    Long getLoserId();

    Long getBattleTime();

    Integer getAttackNumber();

    Date getDate();
}

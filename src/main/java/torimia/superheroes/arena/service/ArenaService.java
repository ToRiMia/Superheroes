package torimia.superheroes.arena.service;

import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.dto.BattleDto;

public interface ArenaService {

    MessageDto battle(BattleDto dto);

    void saveBattleResult(ArenaBattleDto dto);

    void restartNotFinishedFight();
}

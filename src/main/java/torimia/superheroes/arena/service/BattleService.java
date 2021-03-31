package torimia.superheroes.arena.service;

import torimia.superheroes.MessageDto;
import torimia.superheroes.arena.model.dto.BattleDtoResultFromServerDto;
import torimia.superheroes.arena.model.dto.ReceivingBattleDtoFromUser;

public interface BattleService {

    MessageDto battleStart(ReceivingBattleDtoFromUser dto);

    void saveBattleResult(BattleDtoResultFromServerDto dto);

    void restartNotFinishedBattle(); //scheduled restart
}

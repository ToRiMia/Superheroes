package torimia.superheroes.arena;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import torimia.superheroes.arena.model.dto.BattleStatus;
import torimia.superheroes.arena.model.entity.Battle;

import java.util.ArrayList;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

    @Query("select b from Battle b where b.battleStatus <> :status")
    ArrayList<Battle> findAllByBattleStatusIsNotFINISHED_SUCCESSFUL(BattleStatus status);

    default ArrayList<Battle> findAllByBattleStatusIsNotFINISHED_SUCCESSFUL(){
       return findAllByBattleStatusIsNotFINISHED_SUCCESSFUL(BattleStatus.FINISHED_SUCCESSFUL);
    };
}

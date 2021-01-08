package torimia.superheroes.arena;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import torimia.superheroes.arena.model.dto.ArenaBattleView;
import torimia.superheroes.arena.model.dto.FightStatus;
import torimia.superheroes.arena.model.entity.Arena;

import java.util.ArrayList;

@Repository
public interface ArenaRepository extends JpaRepository<Arena, Long> {

    @Query("select a from Arena a where a.fightStatus <> :status")
    ArrayList<Arena> findAllByFightStatusIsNotFINISHED_SUCCESSFUL(FightStatus status);

    default ArrayList<Arena> findAllByFightStatusIsNotFINISHED_SUCCESSFUL(){
       return findAllByFightStatusIsNotFINISHED_SUCCESSFUL(FightStatus.FINISHED_SUCCESSFUL);
    };
}

package torimia.superheroes.arena;

import antlr.collections.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import torimia.superheroes.arena.model.dto.ArenaBattleDto;
import torimia.superheroes.arena.model.dto.ArenaBattleView;
import torimia.superheroes.arena.model.dto.BattleDto;
import torimia.superheroes.arena.model.entity.Arena;

import java.util.ArrayList;

@Repository
public interface ArenaRepository extends JpaRepository<Arena, Long> {

    @Query("select a from Arena a where a.fightStatus <> 2")
    ArrayList<ArenaBattleView> findAllByFightStatusIsNot2();
}

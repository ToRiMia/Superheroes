package torimia.superheroes.arena;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import torimia.superheroes.arena.model.entity.Arena;
import torimia.superheroes.arena.model.entity.BattleParticipant;
import torimia.superheroes.arena.model.entity.BattleParticipantKey;

import java.util.List;

@Repository
public interface BattleParticipantRepository extends JpaRepository<BattleParticipant, BattleParticipantKey> {

    @Query("select bp from BattleParticipant bp where bp.arena = :arena")
    List<BattleParticipant> findAllByArena(Arena arena);

}

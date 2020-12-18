package torimia.superheroes.arena;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import torimia.superheroes.arena.model.entity.Arena;

@Repository
public interface ArenaRepository extends JpaRepository<Arena, Long> {
}

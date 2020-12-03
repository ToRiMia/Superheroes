package torimia.superheroes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import torimia.superheroes.model.entity.Award;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
}

package torimia.superheroes.award;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import torimia.superheroes.award.model.entity.Award;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
}

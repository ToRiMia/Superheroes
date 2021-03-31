package torimia.superheroes.arena;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import torimia.superheroes.arena.model.dto.BattleStatus;
import torimia.superheroes.arena.model.entity.Battle;
import torimia.superheroes.arena.model.entity.BattleParticipant;
import torimia.superheroes.superhero.SuperheroRepository;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class BattleFactory {

    private final BattleRepository repository;
    private final SuperheroRepository superheroRepository;
    private final BattleParticipantRepository battleParticipantRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Battle createBattle() {
        Battle battle = Battle.builder()
                .attackNumber(0)
                .startOfBattle(Instant.now())
                .battleStatus(BattleStatus.STARTED)
                .build();
        repository.save(battle);
        return battle;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void fillBattleParticipant(Long fighter, Battle battle) {
        BattleParticipant battleParticipant = BattleParticipant.builder()
                .battle(battle)
                .superhero(superheroRepository.getOne(fighter))
                .build();
        battleParticipantRepository.save(battleParticipant);
    }
}

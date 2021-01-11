package torimia.superheroes.arena.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import torimia.superheroes.superhero.model.Superhero;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
public class BattleParticipant {

    @EmbeddedId
    private BattleParticipantKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_battle", nullable = false, insertable = false, updatable = false)
    private Battle battle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_participant", nullable = false, insertable = false, updatable = false)
    private Superhero superhero;

    public BattleParticipant(BattleParticipantKey id, Battle battle, Superhero superhero) {
        BattleParticipantKey pk = new BattleParticipantKey();
        pk.setBattleId(battle.getId());
        pk.setParticipantId(superhero.getId());
        this.id = pk;
        this.battle = battle;
        this.superhero = superhero;
    }
}


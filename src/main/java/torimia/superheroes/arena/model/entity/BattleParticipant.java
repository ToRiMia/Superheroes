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
    private Arena arena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_participant", nullable = false, insertable = false, updatable = false)
    private Superhero superhero;

    public BattleParticipant(BattleParticipantKey id, Arena arena, Superhero superhero) {
        BattleParticipantKey pk = new BattleParticipantKey();
        pk.setBattleId(arena.getId());
        pk.setParticipantId(superhero.getId());
        this.id = pk;
        this.arena = arena;
        this.superhero = superhero;
    }
}


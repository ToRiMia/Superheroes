package torimia.superheroes.arena.model.entity;

import lombok.*;
import torimia.superheroes.superhero.model.Superhero;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleParticipant {

    @EmbeddedId
    private BattleParticipantKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_battle", nullable = false, insertable = false, updatable = false)
    private Arena arena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_participant", nullable = false, insertable = false, updatable = false)
    private Superhero superhero;

    @PrePersist
    private void prePersist() {
        if (getId() == null) {
            BattleParticipantKey pk = new BattleParticipantKey();
            pk.setBattleId(arena.getId());
            pk.setParticipantId(superhero.getId());
            setId(pk);
        }
    }
}


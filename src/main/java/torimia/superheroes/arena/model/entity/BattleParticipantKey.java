package torimia.superheroes.arena.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class BattleParticipantKey implements Serializable {

    @Column(name = "id_battle")
    Long battleId;

    @Column(name = "id_participant")
    Long participantId;
}

package torimia.superheroes.arena.model.entity;

import lombok.*;
import torimia.superheroes.arena.model.dto.BattleStatus;
import torimia.superheroes.superhero.model.Superhero;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade=CascadeType.ALL)
    private Superhero winner;

    @NotNull
    private Integer attackNumber;

    @NotNull
    private Instant startOfBattle;

    private Instant endOfBattle;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BattleStatus battleStatus;
}

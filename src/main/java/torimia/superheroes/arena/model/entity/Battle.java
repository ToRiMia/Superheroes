package torimia.superheroes.arena.model.entity;

import lombok.*;
import torimia.superheroes.arena.model.dto.BattleStatus;
import torimia.superheroes.superhero.model.Superhero;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

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
    private Long battleTime;

    @NotNull
    private Integer attackNumber;

    @NotNull
    private Date date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BattleStatus battleStatus;
}

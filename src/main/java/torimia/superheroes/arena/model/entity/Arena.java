package torimia.superheroes.arena.model.entity;

import lombok.*;
import torimia.superheroes.arena.model.dto.FightStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Arena {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long winnerId;

    @NotNull
    private Long loserId;

    @NotNull
    private Long battleTime;

    @NotNull
    private Integer attackNumber;

    @NotNull
    private Date date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FightStatus fightStatus;
}

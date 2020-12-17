package torimia.superheroes.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;

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
    String winner;

    @NotNull
    String nameLoser;

    @NotNull
    Time battleTime;

    @NotNull
    Integer attackNumber;

    @NotNull
    Date date;
}

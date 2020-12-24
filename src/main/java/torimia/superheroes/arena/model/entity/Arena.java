package torimia.superheroes.arena.model.entity;

import lombok.*;

import javax.persistence.*;
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

    Long winnerId;

    Long loserId;

    Long battleTime;

    Integer attackNumber;

    Date date;
}

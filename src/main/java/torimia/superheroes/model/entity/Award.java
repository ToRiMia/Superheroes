package torimia.superheroes.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Rarity rarity;

    @ManyToOne
    private Superhero superhero;
}

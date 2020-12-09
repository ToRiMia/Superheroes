package torimia.superheroes.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Rarity rarity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "superhero_id")
    private Superhero superhero;
}

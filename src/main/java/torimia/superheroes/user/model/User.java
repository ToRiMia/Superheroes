package torimia.superheroes.user.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import torimia.superheroes.superhero.model.Superhero;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "usr")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "deleted_date IS NULL")
@SQLDelete(sql = "UPDATE public.usr SET deleted_date = CURRENT_TIMESTAMP WHERE id =?")
@EqualsAndHashCode(exclude = {"createdSuperhero"})
public class User {

    @Id
    private String id;

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private int age;

    private String email;

    private Instant deletedDate;

    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable
            (
                    name = "user_superheroes",
                    joinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id")},
                    inverseJoinColumns = {@JoinColumn(name = "id_superhero", referencedColumnName = "id", unique = true)}
            )
    private Collection<Superhero> createdSuperhero = new ArrayList<>();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    @PreRemove
    public void deleteUser() {
        this.deletedDate = Instant.now();
    }

    public void addSuperhero(Superhero superhero) {
        createdSuperhero.add(superhero);
    }

    public void deleteSuperhero(Superhero superhero) {
        createdSuperhero.remove(superhero);
    }
}

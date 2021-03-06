package torimia.superheroes.superhero.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import torimia.superheroes.award.model.entity.Award;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table
@Data
@EqualsAndHashCode(exclude = {"listOfFriends", "listOfEnemies", "awards"})
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "deleted_date IS NULL")
@SQLDelete(sql = "UPDATE public.superhero SET deleted_date = CURRENT_TIMESTAMP WHERE id =?")
public class Superhero{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickname;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private int age;

    @Column(length = 1000)
    private String superPower;

    private Integer damage;

    private Integer health;

    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    @ManyToMany()
    private Set<Superhero> listOfFriends = new HashSet<>();

    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    @ManyToMany()
    private Set<Superhero> listOfEnemies = new HashSet<>();

    @Setter(AccessLevel.PRIVATE)
    @Builder.Default
    @OneToMany(mappedBy = "superhero",
            cascade = CascadeType.ALL)
    private Set<Award> awards = new HashSet<>();

    public void addFriend(Superhero friend) {
        listOfEnemies.remove(friend);
        listOfFriends.add(friend);
    }

    public void addEnemy(Superhero enemy) {
        listOfFriends.remove(enemy);
        listOfEnemies.add(enemy);
    }

    public void addAward(Award award) {
        awards.add(award);
        award.setSuperhero(this);
    }

    public void deleteAward(Award award) {
        awards.remove(award);
        award.setSuperhero(null);
    }

    public void deleteEnemy(Superhero enemy) {
        listOfEnemies.remove(enemy);
    }

    public void deleteFriend(Superhero friend) {
        listOfFriends.remove(friend);
    }

    @Override
    public String toString() {
        return "Superhero{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", superPower='" + superPower + '\'' +
                ", listOfFriends=" + listOfFriends.stream().map(Superhero::getId).collect(Collectors.toList()) +
                ", listOfEnemies=" + listOfEnemies.stream().map(Superhero::getId).collect(Collectors.toList())  +
                ", awards=" + awards.stream().map(Award::getId).collect(Collectors.toList())  +
                '}';
    }
}

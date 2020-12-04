package torimia.superheroes.model.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(exclude = {"listOfFriends", "listOfEnemies", "awards"})
public class Superhero {

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

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany()
    private Set<Superhero> listOfFriends = new HashSet<>();

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany()
    private Set<Superhero> listOfEnemies = new HashSet<>();

    @OneToMany(mappedBy = "superhero")
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
    }

    public void deleteEnemy(Superhero enemy) {
        listOfEnemies.remove(enemy);
    }

    public void deleteFriend(Superhero friend) {
        listOfFriends.remove(friend);
    }

    public void deleteAward(Award award) {
        awards.remove(award);
    }
}

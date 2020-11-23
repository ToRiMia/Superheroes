package torimia.superheroes.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Superhero{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String firstName;

    private String lastName;

    private int age;

    @Column(length = 1000)
    private String superPower;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Superhero> listOfFriends;
}

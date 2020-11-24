package torimia.superheroes.model;

import torimia.superheroes.model.entity.Superhero;

import javax.persistence.*;

@Entity
@Table
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Superhero superhero;

    private String friendName;

    public Friend() {
    }

    public Friend(Superhero superhero, String friendName) {
        this.superhero = superhero;
        this.friendName = friendName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Superhero getSuperhero() {
        return superhero;
    }

    public void setSuperhero(Superhero superhero) {
        this.superhero = superhero;
    }
}

package torimia.superheroes.superhero.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuperheroDtoForTop implements SuperheroViewForTop {

    private Long id;

    private String nickname;

    private String firstName;

    private String lastName;

    private Long amount;

    @Override
    public String toString() {
        return "SuperheroDtoForTop{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", amount=" + amount +
                '}';
    }
}

package torimia.superheroes.user.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoResponse {

    private String id;

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private int age;

    private String email;

    private AccountStatus status;

    private Collection<Long> createdSuperheroId;
}

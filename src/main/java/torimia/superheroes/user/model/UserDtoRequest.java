package torimia.superheroes.user.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoRequest {

    private String id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private int age;

    private String email;

    @Builder.Default
    private AccountStatus status = AccountStatus.ACTIVE;
}

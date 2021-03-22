package torimia.superheroes.user.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "usr")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "deleted_date IS NULL")
@SQLDelete(sql = "UPDATE public.usr SET deleted_date = CURRENT_TIMESTAMP WHERE id =?")
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    @PreRemove
    public void deleteUser() {
        this.deletedDate = Instant.now();
    }
}

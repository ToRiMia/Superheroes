package torimia.superheroes.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import torimia.superheroes.user.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(String id);
}

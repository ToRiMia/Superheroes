package torimia.superheroes.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import torimia.superheroes.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

//    @Override
//    @Transactional
//    User save(User user);
}

package torimia.superheroes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import torimia.superheroes.model.Friend;
import torimia.superheroes.model.entity.Superhero;

import java.util.List;

@Repository
public interface FriendRepo extends JpaRepository<Friend, Long> {

    List<Friend> findAllBySuperheroId(Long superheroId);

    void deleteBySuperheroAndFriendName(Superhero superhero, String friendName);


}

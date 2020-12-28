package torimia.superheroes.superhero.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import torimia.superheroes.superhero.model.dto.SuperheroViewForTop;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.superhero.SuperheroRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class SuperheroRepositoryTest extends RepositoryDBForTests {

//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    private SuperheroRepository repository;

    private static final int FOUR_SUPERHEROES = 4;
    private static final int THREE_SUPERHEROES = 3;
    private static final int TWO_SUPERHEROES = 2;
    private static final int ONE_SUPERHERO = 1;

    @BeforeEach
    void initDB() {
        List<Superhero> superheroes = createDummySuperheroes(FOUR_SUPERHEROES);

        addFriends(superheroes.get(1), FOUR_SUPERHEROES);
        addFriends(superheroes.get(3), THREE_SUPERHEROES);
        addFriends(superheroes.get(0), TWO_SUPERHEROES);
        addFriends(superheroes.get(2), ONE_SUPERHERO);

        addEnemies(superheroes.get(3), FOUR_SUPERHEROES);
        addEnemies(superheroes.get(2), THREE_SUPERHEROES);
        addEnemies(superheroes.get(0), TWO_SUPERHEROES);
        addEnemies(superheroes.get(1), ONE_SUPERHERO);

        superheroes.forEach(System.out::println);
    }

    @Test
    void getSuperheroesWithTheBiggestAmountsOfFriends() {
        List<SuperheroViewForTop> superheroes = repository.getSuperheroesWithTheBiggestAmountsOfFriends(THREE_SUPERHEROES);
        superheroes.forEach(this::listToString);
        assertThat(superheroes)
                .hasSize(3);
        assertThat(superheroes.get(0))
                .returns((long) FOUR_SUPERHEROES, SuperheroViewForTop::getAmount);
        assertThat(superheroes.get(1))
                .returns((long) THREE_SUPERHEROES, SuperheroViewForTop::getAmount);
        assertThat(superheroes.get(2))
                .returns((long) TWO_SUPERHEROES, SuperheroViewForTop::getAmount);
    }

    @Test
    void getSuperheroesWithTheBiggestAmountsOfEnemies() {
        List<SuperheroViewForTop> superheroes = repository.getSuperheroesWithTheBiggestAmountsOfEnemies(THREE_SUPERHEROES);
        superheroes.forEach(this::listToString);
        assertThat(superheroes)
                .hasSize(3);
        assertThat(superheroes.get(0))
                .returns((long) FOUR_SUPERHEROES, SuperheroViewForTop::getAmount);
        assertThat(superheroes.get(1))
                .returns((long) THREE_SUPERHEROES, SuperheroViewForTop::getAmount);
        assertThat(superheroes.get(2))
                .returns((long) TWO_SUPERHEROES, SuperheroViewForTop::getAmount);
    }

    private List<Superhero> createDummySuperheroes(int amountOfSuperhero) {
        List<Superhero> superheroes = new ArrayList<>();
        for (int i = 0; i < amountOfSuperhero; i++) {
            Superhero superhero = Superhero.builder().nickname("NICKNAME").firstName("Tiana").
                    lastName("Dobrev").age(26).superPower("Beautiful smile").
                    listOfFriends(new HashSet<>()).listOfEnemies(new HashSet<>()).awards(new HashSet<>())
                    .build();
            repository.save(superhero);
            superheroes.add(superhero);
        }
        return superheroes;
    }

    private void addFriends(Superhero superhero, int amountOfFriends) {
        for (int i = 0; i < amountOfFriends; i++) {
            Superhero friend = Superhero.builder().nickname("NICKNAME").firstName("Tiana").
                    lastName("Dobrev").age(26).superPower("Beautiful smile").
                    listOfFriends(new HashSet<>()).listOfEnemies(new HashSet<>()).awards(new HashSet<>())
                    .build();
            repository.save(friend);
            superhero.addFriend(friend);
        }
        repository.save(superhero);
    }

    private void addEnemies(Superhero superhero, int amountOfEnemies) {
        for (int i = 0; i < amountOfEnemies; i++) {
            Superhero enemy = Superhero.builder().nickname("NICKNAME").firstName("Tiana").
                    lastName("Dobrev").age(26).superPower("Beautiful smile").
                    listOfFriends(new HashSet<>()).listOfEnemies(new HashSet<>()).awards(new HashSet<>())
                    .build();
            repository.save(enemy);
            superhero.addEnemy(enemy);
        }
        repository.save(superhero);
    }

    private void listToString(SuperheroViewForTop view) {
        String format = String.format("SuperheroView [id:%s, amount:%s, firstName:%s, lastName:%s, nickname:%s]", view.getId(), view.getAmount(), view.getFirstName(), view.getLastName(), view.getNickname());
        System.out.println(format);
    }
}

package torimia.superheroes.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import torimia.superheroes.model.dto.SuperheroViewForTop;
import torimia.superheroes.model.entity.Superhero;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ContextConfiguration(initializers = {SuperheroRepositoryTest.Initializer.class})
class SuperheroRepositoryTest extends PostgreSQLContainerForTests {

//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    private SuperheroRepository repository;

    @BeforeEach
    void initDB() {
        List<Superhero> superheroes = createDummySuperheroes(4);

        //винести цифри 3,4,5 у змінні і визвати метод 3 рази, мб
        AtomicInteger i = new AtomicInteger(1);
        superheroes.forEach(superhero -> addFriends(superhero, i.getAndIncrement()));

        superheroes.forEach(System.out::println);
    }

    @Test
    void getSuperheroesWithTheBiggestAmountsOfFriends() {
        List<SuperheroViewForTop> superheroes = repository.getSuperheroesWithTheBiggestAmountsOfFriends(3);
        superheroes.forEach(this::listToString);
        assertThat(superheroes)
                .hasSize(3);
        assertThat(superheroes.get(0))
                .returns(4L, SuperheroViewForTop::getAmount);
        assertThat(superheroes.get(1))
                .returns(3L, SuperheroViewForTop::getAmount);
        assertThat(superheroes.get(2))
                .returns(2L, SuperheroViewForTop::getAmount);
    }

    @Disabled
    @Test
    void getSuperheroesWithTheBiggestAmountsOfEnemies() {
        List<SuperheroViewForTop> superheroes = repository.getSuperheroesWithTheBiggestAmountsOfEnemies(3);
        superheroes.forEach(this::listToString);
        assertThat(superheroes)
                .hasSize(3);
        assertThat(superheroes.get(0))
                .returns(4L, SuperheroViewForTop::getAmount);
        assertThat(superheroes.get(1))
                .returns(3L, SuperheroViewForTop::getAmount);
        assertThat(superheroes.get(2))
                .returns(2L, SuperheroViewForTop::getAmount);
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


    private void listToString(SuperheroViewForTop view) {
        String format = String.format("SuperheroView [id:%s, amount:%s, firstName:%s, lastName:%s, nickname:%s]", view.getId(), view.getAmount(), view.getFirstName(), view.getLastName(), view.getNickname());
        System.out.println(format);
    }
}

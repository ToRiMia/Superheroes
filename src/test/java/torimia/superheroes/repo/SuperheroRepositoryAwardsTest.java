package torimia.superheroes.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import torimia.superheroes.model.dto.AwardView;
import torimia.superheroes.model.entity.Award;
import torimia.superheroes.model.entity.Rarity;
import torimia.superheroes.model.entity.Superhero;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static torimia.superheroes.model.entity.Rarity.*;

@Transactional
@SpringBootTest
class SuperheroRepositoryAwardsTest extends PostgreSQLContainerForTests{

    @Autowired
    private SuperheroRepository repository;

    @BeforeEach
    void initDB() {
        Superhero superhero = createDummySuperhero();

        addDummyAwardToSuperheroWithRarity(superhero, LEGENDARY);
        addDummyAwardToSuperheroWithRarity(superhero, RARE);
        for (int i = 0; i < 4; i++) {
            addDummyAwardToSuperheroWithRarity(superhero, COMMON);
        }

        repository.save(superhero);
    }

    @Test
    void getSuperheroAwards() {
        PageRequest pageable = PageRequest.of(0, 5, Sort.unsorted());
        Page<AwardView> page = repository.getSuperheroAwards(1L, pageable);
        assertThat(page.getContent())
                .hasSize(5);
        assertThat(page.getContent().get(0))
                .returns(LEGENDARY.toString(), AwardView::getRarity);
        assertThat(page.getContent().get(1))
                .returns(RARE.toString(), AwardView::getRarity);
    }

    private Superhero createDummySuperhero() {
        return Superhero.builder().nickname("NICKNAME").firstName("Tiana").
                lastName("Dobrev").age(26).superPower("Beautiful smile").
                listOfFriends(new HashSet<>()).listOfEnemies(new HashSet<>()).awards(new HashSet<>())
                .build();
    }

    private void addDummyAwardToSuperheroWithRarity(Superhero superhero, Rarity rare) {
        Award award = Award.builder().name("Award").rarity(rare).build();
        superhero.addAward(award);
    }

}

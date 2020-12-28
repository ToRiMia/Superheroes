package torimia.superheroes.superhero.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import torimia.superheroes.award.model.dto.AwardView;
import torimia.superheroes.award.model.entity.Award;
import torimia.superheroes.award.model.entity.Rarity;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.superhero.SuperheroRepository;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static torimia.superheroes.award.model.entity.Rarity.*;

@Transactional
@SpringBootTest
class SuperheroRepositoryAwardsTest extends RepositoryDBForTests {

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
        return Superhero.builder().nickname("NICKNAME").firstName("Tiana")
                .lastName("Dobrev").age(26).superPower("Beautiful smile")
                .damage(0).health(100)
                .listOfFriends(new HashSet<>()).listOfEnemies(new HashSet<>()).awards(new HashSet<>())
                .build();
    }

    private void addDummyAwardToSuperheroWithRarity(Superhero superhero, Rarity rare) {
        Award award = Award.builder().name("Award").rarity(rare).build();
        superhero.addAward(award);
    }

}

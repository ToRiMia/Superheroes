package torimia.superheroes.repo;

import org.junit.ClassRule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import torimia.superheroes.model.dto.AwardView;
import torimia.superheroes.model.entity.Award;
import torimia.superheroes.model.entity.Superhero;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static torimia.superheroes.model.entity.Rarity.*;

@SpringBootTest
@ContextConfiguration(initializers = {SuperheroRepositoryTest.Initializer.class})
class SuperheroRepositoryTest {

    private static PostgreSQLContainer postgreSQLContainer;


    static {
        postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
                .withDatabaseName("integration-tests-db")
                .withUsername("postgres")
                .withPassword("2020");
        postgreSQLContainer.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private SuperheroRepository repository;

    @Autowired
    private AwardRepository awardRepository;

    @Test
    void getSuperheroAwards() {
        insertSuperhero();
        PageRequest pageable = PageRequest.of(0, 5, Sort.unsorted());
        Page<AwardView> page = repository.getSuperheroAwards(7L, pageable);
        assertThat(page.getContent())
                .hasSize(5);
    }

    @Disabled
    @Test
    void getSuperheroesWithTheBiggestAmountsOfFriends() {
    }

    @Disabled
    @Test
    void getSuperheroesWithTheBiggestAmountsOfEnemies() {
    }

    private void insertSuperhero() {
        Superhero superhero = Superhero.builder()
                .id(null)
                .nickname("NICKNAME")
                .firstName("Tiana")
                .lastName("Dobrev")
                .age(26)
                .superPower("Beautiful smile")
                .listOfFriends(new HashSet<>())
                .listOfEnemies(new HashSet<>())
                .awards(new HashSet<>())
                .build();

        Award award1 = Award.builder().id(2L).name("Award1").rarity(RARE).superhero(superhero).build();
        Award award2 = Award.builder().id(3L).name("Award2").rarity(RARE).superhero(superhero).build();
        Award award3 = Award.builder().id(4L).name("Award3").rarity(LEGENDARY).superhero(superhero).build();
        Award award4 = Award.builder().id(5L).name("Award4").rarity(COMMON).superhero(superhero).build();
        Award award5 = Award.builder().id(6L).name("Award5").rarity(RARE).superhero(superhero).build();
        Award award6 = Award.builder().id(7L).name("Award6").rarity(LEGENDARY).superhero(superhero).build();

        repository.save(superhero);
        awardRepository.save(award1);
        awardRepository.save(award2);
        awardRepository.save(award3);
        awardRepository.save(award4);
        awardRepository.save(award5);
        awardRepository.save(award6);

        superhero.addAward(award1);
        superhero.addAward(award2);
        superhero.addAward(award3);
        superhero.addAward(award4);
        superhero.addAward(award5);
        superhero.addAward(award6);

        repository.flush();
    }
}

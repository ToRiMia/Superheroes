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
@ContextConfiguration(initializers = {PostgreSQLContainerForTests.Initializer.class})
class PostgreSQLContainerForTests {

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
}

package torimia.superheroes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@SpringBootApplication
public class SuperheroesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperheroesApplication.class, args);
    }

}

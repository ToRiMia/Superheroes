package torimia.superheroes.mappers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.model.entity.Award;
import torimia.superheroes.model.entity.Superhero;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SuperheroMapperTest {

    static Superhero superhero = new Superhero();
    static SuperheroDto superheroDto = new SuperheroDto();
    static Superhero superheroFriend1 = new Superhero();
    static Superhero superheroFriend2 = new Superhero();
    static Superhero superheroEnemy1 = new Superhero();
    static Superhero superheroEnemy2 = new Superhero();
    static Award award1 = new Award();
    static Award award2 = new Award();

    @BeforeAll
    static void init() {
        superheroFriend1.setId((long) 57);
        superheroFriend2.setId((long) 58);
        superheroEnemy1.setId((long) 59);
        superheroEnemy2.setId((long) 60);
        award1.setId((long) 80);
        award2.setId((long) 81);

        //superhero
        superhero.setId((long) 56);
        superhero.setAge(65);
        superhero.setNickname("Toliia");
        superhero.setFirstName("Andrew");
        superhero.setLastName("Semenuk");
        superhero.setSuperPower("No super power");
        superhero.addAward(award1);
        superhero.addEnemy(superheroEnemy1);
        superhero.addFriend(superheroFriend1);
        superhero.addAward(award2);
        superhero.addEnemy(superheroEnemy2);
        superhero.addFriend(superheroFriend2);

        //superheroDto
        superheroDto.setId((long) 56);
        superheroDto.setAge(65);
        superheroDto.setNickname("Toliia");
        superheroDto.setFirstName("Andrew");
        superheroDto.setLastName("Semenuk");
        superheroDto.setSuperPower("No super power");
        superheroDto.setListOfFriendsId(Arrays.asList(superheroFriend1.getId(), superheroFriend2.getId()));
        superheroDto.setListOfEnemiesId(Arrays.asList(superheroEnemy1.getId(), superheroEnemy1.getId()));
        superheroDto.setAwardsId(Arrays.asList(award1.getId(), award2.getId()));

    }

    @Test
    void toDto() {
        SuperheroDto superheroDto = new SuperheroMapperImpl().toDto(superhero);

        assertThat(superheroDto)
                .hasFieldOrPropertyWithValue("id", (long) 56)
                .hasFieldOrPropertyWithValue("age", 65)
                .hasFieldOrPropertyWithValue("nickname", "Toliia")
                .hasFieldOrPropertyWithValue("firstName", "Andrew")
                .hasFieldOrPropertyWithValue("lastName", "Semenuk")
                .hasFieldOrPropertyWithValue("superPower", "No super power");
        assertThat(superheroDto.getListOfFriendsId()).
                contains(superheroFriend1.getId()).
                contains(superheroFriend2.getId());
        assertThat(superheroDto.getListOfEnemiesId()).
                contains(superheroEnemy1.getId()).
                contains(superheroEnemy2.getId());
        assertThat(superheroDto.getAwardsId()).
                contains(award1.getId()).
                contains(award2.getId());
    }

    @Test
    void toEntity() {
        Superhero superhero = new SuperheroMapperImpl().toEntity(superheroDto);

        assertThat(superhero)
                .hasFieldOrPropertyWithValue("id", (long) 56)
                .hasFieldOrPropertyWithValue("age", 65)
                .hasFieldOrPropertyWithValue("nickname", "Toliia")
                .hasFieldOrPropertyWithValue("firstName", "Andrew")
                .hasFieldOrPropertyWithValue("lastName", "Semenuk")
                .hasFieldOrPropertyWithValue("superPower", "No super power");
        assertThat(superhero.getListOfFriends()).hasSize(0);
        assertThat(superhero.getListOfEnemies()).hasSize(0);
        assertThat(superhero.getAwards()).hasSize(0);

    }
}
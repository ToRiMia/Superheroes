package torimia.superheroes.superhero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import torimia.superheroes.award.AwardMapperImpl;
import torimia.superheroes.award.model.dto.AwardDto;
import torimia.superheroes.award.model.dto.AwardView;
import torimia.superheroes.superhero.model.dto.SuperheroAwardsDto;
import torimia.superheroes.superhero.model.dto.SuperheroDto;
import torimia.superheroes.award.model.entity.Award;
import torimia.superheroes.award.model.entity.Rarity;
import torimia.superheroes.superhero.model.Superhero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static torimia.superheroes.award.model.entity.Rarity.RARE;

class SuperheroMapperTest {

    private Superhero superhero;
    private SuperheroDto superheroDto;
    private Superhero superheroFriend1;
    private Superhero superheroFriend2;
    private Superhero superheroEnemy1;
    private Superhero superheroEnemy2;
    private Award award1;
    private Award award2;

    private SuperheroMapperImpl mapper;
    private SuperheroRepository repository;

    private final Long SUPERHERO_ID = 56L;
    private final String SUPERHERO_NICKNAME = "Nickname";
    private final String SUPERHERO_FIRST_NAME = "Name";
    private final String SUPERHERO_LAST_NAME = "Last name";
    private final int SUPERHERO_AGE = 25;
    private final String SUPERHERO_SUPER_POWER = "No super power";
    private final Integer SUPERHERO_DAMAGE = 0;
    private final Integer SUPERHERO_HEALTH = 100;

    private final String AWARD_NAME = "Some name";
    private final Rarity AWARD_RARITY = RARE;


    @BeforeEach
    void setUp() {
        mapper = new SuperheroMapperImpl(new AwardMapperImpl(), repository);

        superhero = new Superhero();

        superheroDto = new SuperheroDto();
        superheroFriend1 = new Superhero();
        superheroFriend2 = new Superhero();
        superheroEnemy1 = new Superhero();
        superheroEnemy2 = new Superhero();

        superheroFriend1.setId(57L);
        superheroFriend2.setId(58L);
        superheroEnemy1.setId(59L);
        superheroEnemy2.setId(60L);

        award1 = Award.builder()
                .id(80L)
                .name(AWARD_NAME)
                .rarity(AWARD_RARITY)
                .build();
        award2 = Award.builder()
                .id(81L)
                .name(AWARD_NAME)
                .rarity(AWARD_RARITY)
                .build();

        superhero = Superhero.builder()
                .id(SUPERHERO_ID)
                .nickname(SUPERHERO_NICKNAME)
                .firstName(SUPERHERO_FIRST_NAME)
                .lastName(SUPERHERO_LAST_NAME)
                .age(SUPERHERO_AGE)
                .superPower(SUPERHERO_SUPER_POWER)
                .damage(SUPERHERO_DAMAGE)
                .health(SUPERHERO_HEALTH)
                .listOfFriends(Set.of(superheroFriend1, superheroFriend2))
                .listOfEnemies(Set.of(superheroEnemy1, superheroEnemy2))
                .awards(Set.of(award1, award2))
                .build();
    }


    @Test
    void toDto() {
        SuperheroDto superheroDto = mapper.toDto(superhero);

        assertThat(superheroDto)
                .returns(SUPERHERO_ID, SuperheroDto::getId)
                .returns(SUPERHERO_NICKNAME, SuperheroDto::getNickname)
                .returns(SUPERHERO_FIRST_NAME, SuperheroDto::getFirstName)
                .returns(SUPERHERO_LAST_NAME, SuperheroDto::getLastName)
                .returns(SUPERHERO_AGE, SuperheroDto::getAge)
                .returns(SUPERHERO_DAMAGE, SuperheroDto::getDamage)
                .returns(SUPERHERO_HEALTH, SuperheroDto::getHealth)
                .returns(SUPERHERO_SUPER_POWER, SuperheroDto::getSuperPower);
        assertThat(superheroDto.getListOfFriendsId())
                .contains(superheroFriend1.getId())
                .contains(superheroFriend2.getId())
                .hasSize(2);
        assertThat(superheroDto.getListOfEnemiesId())
                .contains(superheroEnemy1.getId())
                .contains(superheroEnemy2.getId())
                .hasSize(2);
        assertThat(superheroDto.getAwardsId())
                .contains(award1.getId())
                .contains(award2.getId())
                .hasSize(2);
    }

    @Test
    void toEntity() {
        superheroDto = SuperheroDto.builder()
                .id(SUPERHERO_ID)
                .nickname(SUPERHERO_NICKNAME)
                .firstName(SUPERHERO_FIRST_NAME)
                .lastName(SUPERHERO_LAST_NAME)
                .age(SUPERHERO_AGE)
                .superPower(SUPERHERO_SUPER_POWER)
                .damage(SUPERHERO_DAMAGE)
                .health(SUPERHERO_HEALTH)
                .listOfFriendsId(Arrays.asList(superheroFriend1.getId(), superheroFriend2.getId()))
                .listOfEnemiesId(Arrays.asList(superheroEnemy1.getId(), superheroEnemy2.getId()))
                .awardsId(Arrays.asList(award1.getId(), award2.getId()))
                .build();

        Superhero superhero = mapper.toEntity(superheroDto);

        assertThat(superhero)
                .returns(SUPERHERO_ID, Superhero::getId)
                .returns(SUPERHERO_NICKNAME, Superhero::getNickname)
                .returns(SUPERHERO_FIRST_NAME, Superhero::getFirstName)
                .returns(SUPERHERO_LAST_NAME, Superhero::getLastName)
                .returns(SUPERHERO_AGE, Superhero::getAge)
                .returns(SUPERHERO_SUPER_POWER, Superhero::getSuperPower)
                .returns(SUPERHERO_DAMAGE, Superhero::getDamage)
                .returns(SUPERHERO_HEALTH, Superhero::getHealth);
        assertThat(superhero.getListOfFriends()).isEmpty();
        assertThat(superhero.getListOfEnemies()).isEmpty();
        assertThat(superhero.getAwards()).isEmpty();
    }

    @Test
    void toEntityUpdate() {
        superheroDto = SuperheroDto.builder()
                .id(30L)
                .nickname("Pavillo")
                .firstName("Pavel")
                .lastName("Reno")
                .age(29)
                .superPower("Strong")
                .listOfFriendsId(Arrays.asList(35L, 36L))
                .listOfEnemiesId(Arrays.asList(37L, 38L))
                .awardsId(Arrays.asList(39L, 40L))
                .build();

        mapper.toEntityUpdate(superheroDto, superhero);

        assertThat(superhero)
                .returns(SUPERHERO_ID, Superhero::getId)
                .returns(superheroDto.getNickname(), Superhero::getNickname)
                .returns(superheroDto.getFirstName(), Superhero::getFirstName)
                .returns(superheroDto.getLastName(), Superhero::getLastName)
                .returns(superheroDto.getAge(), Superhero::getAge)
                .returns(superheroDto.getSuperPower(), Superhero::getSuperPower);
        assertThat(superhero.getListOfFriends())
                .contains(superheroFriend1)
                .contains(superheroFriend2)
                .hasSize(2);
        assertThat(superhero.getListOfEnemies())
                .contains(superheroEnemy1)
                .contains(superheroEnemy2)
                .hasSize(2);
        assertThat(superhero.getAwards())
                .contains(award1)
                .contains(award2)
                .hasSize(2);
    }

    @Test
    void toDtoSuperheroAwards() {
        AwardDto awardDto1 = AwardDto.builder()
                .id(45L)
                .name("За спасіння 1000 людей від землетрусу")
                .rarity("RARE")
                .build();

        AwardDto awardDto2 = AwardDto.builder()
                .id(46L)
                .name("За спасіння 10-и людей від потопу")
                .rarity("COMMON")
                .build();

        List<AwardView> awards = new ArrayList<>();
        awards.add(awardDto1);
        awards.add(awardDto2);

        SuperheroAwardsDto superheroAwardsDto = mapper.toDtoSuperheroAwards(superhero, awards);

        assertThat(superheroAwardsDto)
                .returns(SUPERHERO_ID, SuperheroAwardsDto::getId)
                .returns(SUPERHERO_NICKNAME, SuperheroAwardsDto::getNickname)
                .returns(SUPERHERO_FIRST_NAME, SuperheroAwardsDto::getFirstName)
                .returns(SUPERHERO_LAST_NAME, SuperheroAwardsDto::getLastName)
                .returns(SUPERHERO_AGE, SuperheroAwardsDto::getAge)
                .returns(SUPERHERO_SUPER_POWER, SuperheroAwardsDto::getSuperPower);
        assertThat(superheroAwardsDto.getListOfFriendsId())
                .contains(superheroFriend1.getId())
                .contains(superheroFriend2.getId())
                .hasSize(2);
        assertThat(superheroAwardsDto.getListOfEnemiesId())
                .contains(superheroEnemy1.getId())
                .contains(superheroEnemy2.getId())
                .hasSize(2);
        assertThat(superheroAwardsDto.getAwards())
                .contains(awardDto1)
                .contains(awardDto2)
                .hasSize(2);

        assertThat(superheroAwardsDto.getAwards())
                .anySatisfy(awardDto -> assertThat(awardDto)
                        .returns(awardDto1.getId(), AwardDto::getId)
                        .returns(awardDto1.getName(), AwardDto::getName)
                        .returns(awardDto1.getRarity(), AwardDto::getRarity))
                .anySatisfy(awardDto -> assertThat(awardDto)
                        .returns(awardDto2.getId(), AwardDto::getId)
                        .returns(awardDto2.getName(), AwardDto::getName)
                        .returns(awardDto2.getRarity(), AwardDto::getRarity));
    }
}
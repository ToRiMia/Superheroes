package torimia.superheroes.award;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import torimia.superheroes.award.AwardMapperImpl;
import torimia.superheroes.award.model.dto.AwardDto;
import torimia.superheroes.award.model.entity.Award;
import torimia.superheroes.award.model.entity.Rarity;
import torimia.superheroes.superhero.model.Superhero;

import static org.assertj.core.api.Assertions.assertThat;
import static torimia.superheroes.award.model.entity.Rarity.COMMON;
import static torimia.superheroes.award.model.entity.Rarity.RARE;

class AwardMapperTest {

    private AwardMapperImpl mapper;
    private Award award;

    private final Long AWARD_ID = 50L;
    private final String AWARD_NAME = "За спасіння 1000 людей від землетрусу";
    private final Rarity AWARD_RARITY = RARE;
    private final Superhero AWARD_SUPERHERO = new Superhero();


    @BeforeEach
    void setUp() {
        mapper = new AwardMapperImpl();

        award = Award.builder()
                .id(AWARD_ID)
                .name(AWARD_NAME)
                .rarity(AWARD_RARITY)
                .superhero(AWARD_SUPERHERO)
                .build();
    }

    @Test
    void toDto() {
        AwardDto awardDto = mapper.toDto(award);

        assertThat(awardDto)
                .returns(AWARD_ID, AwardDto::getId)
                .returns(AWARD_NAME, AwardDto::getName)
                .returns(AWARD_RARITY.toString(), AwardDto::getRarity);
    }

    @Test
    void toEntity() {
        AwardDto awardDto = AwardDto.builder()
                .id(AWARD_ID)
                .name(AWARD_NAME)
                .rarity(AWARD_RARITY.toString())
                .build();

        Award award = mapper.toEntity(awardDto);

        assertThat(award)
                .returns(AWARD_ID, Award::getId)
                .returns(AWARD_NAME, Award::getName)
                .returns(AWARD_RARITY, Award::getRarity)
                .returns(null, Award::getSuperhero);
    }

    @Test
    void toEntityUpdate() {
        AwardDto awardDto = AwardDto.builder()
                .id(51L)
                .name("За спасіння 100 людей від потопу")
                .rarity(COMMON.toString())
                .build();

        mapper.toEntityUpdate(awardDto, award);

        assertThat(award)
                .returns(AWARD_ID, Award::getId)
                .returns(awardDto.getName(), Award::getName)
                .returns(Rarity.valueOf(awardDto.getRarity()), Award::getRarity)
                .returns(AWARD_SUPERHERO, Award::getSuperhero);
    }
}
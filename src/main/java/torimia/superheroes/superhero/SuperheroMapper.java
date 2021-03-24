package torimia.superheroes.superhero;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.arena.model.entity.BattleParticipant;
import torimia.superheroes.award.AwardMapper;
import torimia.superheroes.award.model.dto.AwardView;
import torimia.superheroes.award.model.entity.Award;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.superhero.model.dto.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(imports = {Collectors.class, Award.class, SuperheroRepository.class}, componentModel = "spring", uses = {AwardMapper.class, SuperheroRepository.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SuperheroMapper {

    @Mapping(target = "listOfFriendsId", source = "listOfFriends")
    @Mapping(target = "listOfEnemiesId", source = "listOfEnemies")
    @Mapping(target = "awardsId", source = "awards")
    SuperheroDto toDto(Superhero superhero);

    default List<Long> toIdsSuperhero(Collection<Superhero> superheroes) {
        return superheroes.stream().map(Superhero::getId).collect(Collectors.toList());
    }

    default Long toIdSuperhero(Superhero superhero) {
        return superhero.getId();
    }

    Set<Superhero> map(List<Long> value);

    @Mapping(target = "listOfFriends", ignore = true)
    @Mapping(target = "listOfEnemies", ignore = true)
    @Mapping(target = "awards", ignore = true)
    Superhero toEntity(SuperheroDto superheroDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "listOfFriends", ignore = true)
    @Mapping(target = "listOfEnemies", ignore = true)
    @Mapping(target = "awards", ignore = true)
    void toEntityUpdate(SuperheroDto superheroDto, @MappingTarget Superhero superhero);

    @Mapping(target = "listOfFriendsId", source = "superhero.listOfFriends")
    @Mapping(target = "listOfEnemiesId", source = "superhero.listOfEnemies")
    @Mapping(target = "awards", source = "awards")
    SuperheroAwardsDto toDtoSuperheroAwards(Superhero superhero, List<AwardView> awards);

    SuperheroDtoForBattle toDtoForBattle(Superhero superhero);

    List<SuperheroDtoForBattle> toSuperheroDtoForBattle(List<BattleParticipant> value);

    default SuperheroDtoForBattle toSuperheroDtoForBattleFromBattleParticipant(BattleParticipant battleParticipant) {
        return SuperheroDtoForBattle.builder()
                .id(battleParticipant.getSuperhero().getId())
                .nickname(battleParticipant.getSuperhero().getNickname())
                .health(battleParticipant.getSuperhero().getHealth())
                .damage(battleParticipant.getSuperhero().getDamage())
                .build();
    }

    SuperheroDtoForTop toDtoForTop(SuperheroViewForTop superheroViewForTop);
}

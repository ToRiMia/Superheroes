package torimia.superheroes.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.model.dto.*;
import torimia.superheroes.model.entity.Award;
import torimia.superheroes.model.entity.Superhero;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(imports = {Collectors.class, Award.class}, componentModel = "spring", uses = AwardMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SuperheroMapper {

    @Mapping(target = "listOfFriendsId", source = "listOfFriends")
    @Mapping(target = "listOfEnemiesId", source = "listOfEnemies")
    @Mapping(target = "awardsId", source = "awards")
    SuperheroDto toDto(Superhero superhero);

    default List<Long> toIdsSuperhero(Set<Superhero> superheroes) {
        return superheroes.stream().map(Superhero::getId).collect(Collectors.toList());
    }

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

    SuperheroDtoForTop toDtoForTop(SuperheroViewForTop superhero);

    SuperheroDtoForBattle toDtoForBattle(Superhero superhero);
}

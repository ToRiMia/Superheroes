package torimia.superheroes.mappers;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.mapstruct.*;
import torimia.superheroes.model.dto.AwardView;
import torimia.superheroes.model.dto.SuperheroAwardsDto;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.model.entity.Superhero;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import torimia.superheroes.model.entity.Award;

@Mapper(imports = {Collectors.class, Award.class}, componentModel = "spring")
public interface SuperheroMapper {

    @Mapping(target = "listOfFriendsId", source = "listOfFriends")
    @Mapping(target = "listOfEnemiesId", source = "listOfEnemies")
    @Mapping(target = "awardsId",source = "awards")
    SuperheroDto toDto(Superhero superhero);

    default List<Long> toIdsAwards(Set<Award> awards){
        return awards.stream().map(Award::getId).collect(Collectors.toList());
    };

    default List<Long> toIdsSuperhero(Set<Superhero> superheroes){
        return superheroes.stream().map(Superhero::getId).collect(Collectors.toList());
    };

    @Mapping(target = "listOfFriends", ignore = true)
    @Mapping(target = "listOfEnemies", ignore = true)
    Superhero toEntity(SuperheroDto superheroDto);

    @Mapping(target = "listOfFriends", ignore = true)
    @Mapping(target = "listOfEnemies", ignore = true)
    @Mapping(target = "id", ignore = true)
    void toEntityUpdate(SuperheroDto superheroDto, @MappingTarget Superhero superhero);

    @Mapping(target = "listOfFriendsId", source = "superhero.listOfFriends")
    @Mapping(target = "listOfEnemiesId", source = "superhero.listOfEnemies")
    @Mapping(target = "awards", source = "awards")
    SuperheroAwardsDto toDtoSuperheroAwards(Superhero superhero, List<AwardView> awards);

}

package torimia.superheroes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.model.dto.SuperheroDto;
import torimia.superheroes.model.entity.Superhero;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class, componentModel = "spring")
public interface SuperheroMapper {

    @Mapping(target = "listOfFriendsId",
            expression = "java(superhero.getListOfFriends().stream().map(Superhero::getId).collect(Collectors.toList()))")
    @Mapping(target = "listOfEnemiesId",
            expression = "java(superhero.getListOfEnemies().stream().map(Superhero::getId).collect(Collectors.toList()))")
    SuperheroDto toDto(Superhero superhero);

//    default List<Long> toIds(List<Superhero> superheroList){
//        return superheroList.stream().map(Superhero::getId).collect(Collectors.toList());
//    }

    @Mapping(target = "listOfFriends", ignore = true)
    @Mapping(target = "listOfEnemies", ignore = true)
    Superhero toEntity(SuperheroDto superheroDto);

    @Mapping(target = "listOfFriends", ignore = true)
    @Mapping(target = "listOfEnemies", ignore = true)
    @Mapping(target = "id", ignore = true)
    void toEntityUpdate(SuperheroDto superheroDto, @MappingTarget Superhero superhero);
}

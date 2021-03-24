package torimia.superheroes.user;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.superhero.SuperheroMapper;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.model.UserDtoRequest;
import torimia.superheroes.user.model.UserDtoResponse;

@Mapper(componentModel = "spring", uses = SuperheroMapper.class)
public interface UserMapper {

    @Mapping(target = "createdSuperheroId", source = "createdSuperhero")
    UserDtoResponse toDtoResponse(User user);

    @Mapping(target = "createdSuperhero", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    User toEntity(UserDtoRequest dto);

    @Mapping(target = "createdSuperhero", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    void toEntityUpdate(UserDtoRequest dto, @MappingTarget User user);

    UserRepresentation toUserRepresentation(UserDtoRequest dto);
}

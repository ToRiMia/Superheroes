package torimia.superheroes.user;


import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.model.UserDtoRequest;
import torimia.superheroes.user.model.UserDtoResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDtoResponse toDtoResponse(User user);

    User toEntity(UserDtoRequest dto);

    @Mapping(target = "id", ignore = true)
    void toEntityUpdate(UserDtoRequest dto, @MappingTarget User user);

    UserRepresentation toUserRepresentation(UserDtoRequest dto);

    @Mapping(target = "id", ignore = true)
    void toEntityKeycloakUpdate(UserDtoRequest dto, @MappingTarget UserRepresentation user);

}

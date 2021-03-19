package torimia.superheroes.user;

import org.keycloak.representations.account.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.model.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto dto);

    @Mapping(target = "id", ignore = true)
    void toEntityUpdate(UserDto dto, @MappingTarget User user);


    UserRepresentation toEntityKeycloak(UserDto dto);

    @Mapping(target = "id", ignore = true)
    void toEntityKeycloakUpdate(UserDto dto, @MappingTarget UserRepresentation user);

}

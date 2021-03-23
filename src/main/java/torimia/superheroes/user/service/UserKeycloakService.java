package torimia.superheroes.user.service;

import torimia.superheroes.user.model.UserDtoRequest;

public interface UserKeycloakService {

    String create(UserDtoRequest dto);

    void update(String id, UserDtoRequest dto);

    void delete(String id);
}

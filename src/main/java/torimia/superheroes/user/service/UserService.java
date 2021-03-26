package torimia.superheroes.user.service;

import torimia.superheroes.user.model.UserDtoRequest;
import torimia.superheroes.user.model.UserDtoResponse;

public interface UserService {

    UserDtoResponse getById(String id);

    UserDtoResponse create(UserDtoRequest dto);

    UserDtoResponse update(String id, UserDtoRequest dto);

    void delete(String id);
}

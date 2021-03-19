package torimia.superheroes.user.service;

import torimia.superheroes.user.model.UserDtoRequest;
import torimia.superheroes.user.model.UserDtoResponse;

public interface UserService {

    UserDtoResponse create(UserDtoRequest dto);

    UserDtoResponse getById(String id);

    UserDtoResponse update(String id, UserDtoRequest dto);

    void delete(String id);
}

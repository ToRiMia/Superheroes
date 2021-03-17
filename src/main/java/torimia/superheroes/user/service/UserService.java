package torimia.superheroes.user.service;

import torimia.superheroes.user.model.UserDto;

public interface UserService {

    UserDto create(UserDto dto);

    UserDto getById(Long id);

    UserDto update(Long id, UserDto dto);

    void delete(Long id);

    //void restorePassword();
}

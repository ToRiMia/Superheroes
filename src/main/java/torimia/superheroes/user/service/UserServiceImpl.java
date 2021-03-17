package torimia.superheroes.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import torimia.superheroes.user.UserMapper;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.model.UserDto;
import torimia.superheroes.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserKeycloakService keycloakService; // TODO: 17.03.21 мб інтерфейс краще

    @Override
    public UserDto create(UserDto dto) {
        keycloakService.create(dto);
        User user = mapper.toEntity(dto);
        user.setId(null);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public UserDto getById(Long id) {
        return null;
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

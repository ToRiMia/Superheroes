package torimia.superheroes.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import torimia.superheroes.user.UserMapper;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.model.UserDtoRequest;
import torimia.superheroes.user.model.UserDtoResponse;
import torimia.superheroes.user.repository.UserRepository;

import javax.ws.rs.ForbiddenException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserKeycloakService keycloakService;

    @Override
    public UserDtoResponse create(UserDtoRequest dto) {
        String userId = keycloakService.create(dto);
        User user = mapper.toEntity(dto);
        user.setId(userId);
        return mapper.toDtoResponse(repository.save(user));
    }

    @Override
    public UserDtoResponse getById(String id) {
        return mapper.toDtoResponse(repository.getOne(id));
    }

    @Override
    public UserDtoResponse update(String id, UserDtoRequest dto) {
        User user = repository.getOne(id);
        if (!dto.getUsername().equals(user.getUsername())) {
            throw new ForbiddenException("It is forbidden to change username!");
        }

        keycloakService.update(id, dto);
        mapper.toEntityUpdate(dto, user);
        return mapper.toDtoResponse(repository.save(user));
    }

    @Override
    public void delete(String id) {
        keycloakService.delete(id);
        repository.findUserById(id).ifPresent(user -> repository.deleteById(id));
    }
}


package torimia.superheroes.user.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserKeycloakService keycloakService; // TODO: 17.03.21 мб інтерфейс краще

    @Override
    public UserDtoResponse create(UserDtoRequest dto) {
        String userId = keycloakService.create(dto);
        User user = mapper.toEntity(dto);
        user.setId(userId);
        return mapper.toDtoResponse(repository.save(user));
    }

    @Override
    public UserDtoResponse getById(String id) {
        checkIdForIdentity(id);
        return mapper.toDtoResponse(repository.getOne(id));
    }

    @Override
    public UserDtoResponse update(String id, UserDtoRequest dto) {
        checkIdForIdentity(id); // TODO: 22.03.21 а що ,якщо акаунт не існує?

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
        checkIdForIdentity(id);
        repository.findUserById(id).ifPresent(user -> repository.deleteById(id));
    }

    private void checkIdForIdentity(String id) {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = principal.getName();// TODO: 22.03.21 німагу кидати юзера з контроллера(

        if (!userId.equals(id)) {
            throw new ForbiddenException("It is forbidden to interact with another account!");
        }
    }
}


package torimia.superheroes.user;

import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserIdFromPathToUserConverter implements Converter<SecurityContextHolder, User> {

    private final UserRepository repository;

    @Override
    public User convert(SecurityContextHolder holder) {
        KeycloakPrincipal principal = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = principal.getName();
        return repository.getOne(userId);
    }
}


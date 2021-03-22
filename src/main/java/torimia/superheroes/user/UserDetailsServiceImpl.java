package torimia.superheroes.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.model.UserDto;
import torimia.superheroes.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " not exist!"));
        return new UserDto(user);
    }
}

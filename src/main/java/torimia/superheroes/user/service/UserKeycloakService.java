package torimia.superheroes.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import torimia.superheroes.user.UserMapper;
import torimia.superheroes.user.model.UserDto;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserKeycloakService {

    private static final String serverUrl = "http://localhost:8082/auth";
    private static final String realm = "SpringBootKeycloak";
    private static final String clientId = "torimia"; // TODO: 17.03.21 it is already in props
//    String clientSecret = "a200cdf6-ad72-4f6c-af73-5b8e1cc48876";

    private final UserMapper mapper;
    private Keycloak keycloak = createKeycloak();

    private Keycloak createKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
//                .clientSecret(clientSecret) //
                .username("admin1")
                .password("admin")
                .build();
    }

    public void create(UserDto dto) {
        UserRepresentation user = toUserRepresentation(dto);

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(user);
        log.info("Response: {}", response.getStatus());

        String userId = CreatedResponseUtil.getCreatedId(response);
        log.info("User created with userId: {}", userId);

        UserResource userResource = usersResource.get(userId);

        setPassword(dto.getPassword(), userResource);
    }

    private void setPassword(String password, UserResource userResource) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        userResource.resetPassword(passwordCred);
    }

    private UserRepresentation toUserRepresentation(UserDto dto) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRealmRoles(Collections.singletonList("user"));
        return user;
    }

    public UserDto getById(Long id) {
        return null;
    }

    public UserDto update(Long id, UserDto dto) {
        return null;
    }

    public void delete(Long id) {

    }
}

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
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.util.JsonSerialization;
import org.springframework.stereotype.Component;
import torimia.superheroes.user.UserMapper;
import torimia.superheroes.user.model.UserDto;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserKeycloakService {

    //    private static final String serverUrl = "http://localhost:8082/auth/realms/SpringBootKeycloak/protocol/openid-connect/token";
    private static final String serverUrl = "http://localhost:8082/auth";
    private static final String realm = "SpringBootKeycloak";
    private static final String clientId = "torimia"; // TODO: 17.03.21 it is already in props
    private static final String clientSecret = "165e6144-7051-4935-9aaa-323fc75257cd";

    private final UserMapper mapper;
    private Keycloak keycloak = createKeycloak();

    private Keycloak createKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
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
        log.info("User created with userId: {}", userId);

//        CredentialRepresentation passwordCredential = new CredentialRepresentation();
//        passwordCredential.setTemporary(false);
//        passwordCredential.setType(CredentialRepresentation.PASSWORD);
//        passwordCredential.setValue(dto.getPassword());
//
//        userResource.resetPassword(passwordCredential);

        RoleRepresentation testerRealmRole = realmResource.roles().get("user").toRepresentation();
        userResource.roles().realmLevel().add(Arrays.asList(testerRealmRole));

   //     setPassword(dto.getPassword(), userResource);
    }

    private void setPassword(String password, UserResource userResource) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        userResource.resetPassword(passwordCred);
    }

    private UserRepresentation toUserRepresentation(UserDto dto) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(dto.getPassword());

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(dto.getUsername());
        keycloakUser.setFirstName(dto.getFirstName());
        keycloakUser.setLastName(dto.getLastName());
        keycloakUser.setEmail(dto.getEmail());
        keycloakUser.setCredentials(Collections.singletonList(credential));
        keycloakUser.setEnabled(true);

        return keycloakUser;
    }

    public UserDto getById(Long id) {
        return null;
    }

    public UserDto update(Long id, UserDto dto) {
        return null;
    }

    public void delete(Long id) {

    }

    private static void handleClientErrorException(ClientErrorException e) {
        e.printStackTrace();
        Response response = e.getResponse();
        try {
            log.error("status: " + response.getStatus());
            log.error("reason: " + response.getStatusInfo().getReasonPhrase());
            Map error = JsonSerialization.readValue((ByteArrayInputStream) response.getEntity(), Map.class);
            log.error("error: " + error.get("error"));
            log.error("error_description: " + error.get("error_description"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

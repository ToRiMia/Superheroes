package torimia.superheroes.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import torimia.superheroes.user.UserMapper;
import torimia.superheroes.user.model.UserDtoRequest;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserKeycloakServiceImpl implements UserKeycloakService {

    @Value("${keycloak.realm}")
    private String realm;

    private final UserMapper mapper;
    private final Keycloak keycloak;

    @Override
    public String create(UserDtoRequest dto) {
        UserRepresentation user = toUserRepresentation(dto);

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(user);

        String userId = CreatedResponseUtil.getCreatedId(response);
        log.info("CheckUser created with userId: {}", userId);

        addRoleUser(realmResource, usersResource, userId);
        return userId;
    }

    private UserRepresentation toUserRepresentation(UserDtoRequest dto) {
        UserRepresentation keycloakUser = mapper.toUserRepresentation(dto);
        keycloakUser.setCredentials(Collections.singletonList(getCredentialPassword(dto.getPassword())));
        keycloakUser.setEnabled(true);
        return keycloakUser;
    }

    private void addRoleUser(RealmResource realmResource, UsersResource usersResource, String userId) {
        UserResource userResource = usersResource.get(userId);

        RoleRepresentation userRealmRole = realmResource.roles().get("user").toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(userRealmRole));
    }

    private CredentialRepresentation getCredentialPassword(String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }

    @Override
    public void update(String id, UserDtoRequest dto) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        UserResource userResource = usersResource.get(id);
        userResource.update(mapper.toUserRepresentation(dto));
    }

    @Override
    public void delete(String id) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        UserResource userResource = usersResource.get(id);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setEnabled(false);
        userResource.update(userRepresentation);
    }
}

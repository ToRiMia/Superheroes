package torimia.superheroes.user.controller;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import torimia.superheroes.user.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import java.security.Principal;

public class HeaderUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(CheckUser.class) != null;
    }

    @Override
    public torimia.superheroes.user.model.User resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) {

        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();

        String pathId = request.getServletPath().split("/")[request.getServletPath().split("/").length - 1];

        if (!userId.equals(pathId)) {
            throw new ForbiddenException("It is forbidden to interact with another account!");
        }

        AccessToken token = ((SimpleKeycloakAccount) ((KeycloakAuthenticationToken) principal).getDetails()).getKeycloakSecurityContext().getToken();

        return User.builder()
                .id(userId)
                .username(token.getNickName())
                .firstName(token.getGivenName())
                .lastName(token.getFamilyName())
                .email(token.getEmail())
                .build();
    }
}


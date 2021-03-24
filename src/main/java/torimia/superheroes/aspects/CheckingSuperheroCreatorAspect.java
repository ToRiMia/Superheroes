package torimia.superheroes.aspects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import torimia.superheroes.superhero.SuperheroRepository;
import torimia.superheroes.superhero.model.Superhero;
import torimia.superheroes.user.model.User;
import torimia.superheroes.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import java.security.Principal;
import java.util.LinkedHashMap;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckingSuperheroCreatorAspect {

    private final UserRepository userRepository;
    private final SuperheroRepository superheroRepository;

    @Pointcut("execution(public * torimia.superheroes.superhero.controller.SuperheroesController.*(..))")
    public void callAllPublicMethods() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void callGetAndPostMapping() {
    }

    @Before("callAllPublicMethods() && !callGetAndPostMapping()")
    public void verificationSuperheroCreatorInvocation() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Superhero superhero = getSuperhero(request);
        User user = getUser(request);

        if (!user.getCreatedSuperhero().contains(superhero)) {
            throw new ForbiddenException("It is forbidden to interact with another account!");
        }
    }

    private User getUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();
        return userRepository.getOne(userId);
    }

    private Superhero getSuperhero(HttpServletRequest request) {
        LinkedHashMap<String, String> attributes = (LinkedHashMap<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long superheroId = Long.valueOf(attributes.get("id"));
        return superheroRepository.getOne(superheroId);
    }
}



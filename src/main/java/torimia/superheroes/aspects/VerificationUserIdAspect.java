package torimia.superheroes.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import java.security.Principal;
import java.util.LinkedHashMap;

@Aspect
@Component
public class VerificationUserIdAspect {

    @Before("execution(public * torimia.superheroes.user.controller.UserController.*(..)) " +
            "&& !(execution(* torimia.superheroes.user.controller.UserController.create(..)))")
    public void verificationUserIdInvocation() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        LinkedHashMap<String,String> attributes = (LinkedHashMap<String,String>)request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String pathId = attributes.get("id");

        Principal principal = request.getUserPrincipal();
        String userId = principal.getName();

        if (!userId.equals(pathId)) {
            throw new ForbiddenException("It is forbidden to interact with another account!");
        }
    }
}



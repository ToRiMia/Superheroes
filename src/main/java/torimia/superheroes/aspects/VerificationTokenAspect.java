package torimia.superheroes.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import torimia.superheroes.exceptions.VerificationTokenException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class VerificationTokenAspect {

    @Before("@annotation(torimia.superheroes.arena.annotations.TokenCheckable)")
    public void verificationTokenInvocation() throws VerificationTokenException {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        String token = request.getHeader("token");
        if (!token.equals("Arena response"))
            throw new VerificationTokenException("Wrong token, access denied");
    }
}



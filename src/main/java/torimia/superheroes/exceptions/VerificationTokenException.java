package torimia.superheroes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class VerificationTokenException extends RuntimeException {

    public VerificationTokenException(String message) {
        super(message);
    }
}

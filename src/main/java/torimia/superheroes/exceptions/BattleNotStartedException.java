package torimia.superheroes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BattleNotStartedException extends RuntimeException{
    public BattleNotStartedException(String message) {
        super(message);
    }
}

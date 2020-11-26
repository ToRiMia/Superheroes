package torimia.superheroes.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import torimia.superheroes.exceptions.AddingToListException;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound() {
        return new ResponseEntity<>("Superhero with this id does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddingToListException.class)
    public ResponseEntity<String> handleAddingToList(AddingToListException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}


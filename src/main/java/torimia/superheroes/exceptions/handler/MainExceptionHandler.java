package torimia.superheroes.exceptions.handler;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import torimia.superheroes.exceptions.AddingToListException;

import javax.persistence.EntityNotFoundException;
import javax.security.sasl.AuthenticationException;
import java.rmi.AccessException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationResponse response = toValidationResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ValidationResponse toValidationResponse(MethodArgumentNotValidException ex) {
        Map<String, String> data = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            data.put(fieldName, errorMessage);
        });
        ValidationResponse response = new ValidationResponse("Validation failed", data);
        return response;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound() {
        return new ResponseEntity<>("Entity with this id does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddingToListException.class)
    public ResponseEntity<String> handleAddingToList(AddingToListException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }
}


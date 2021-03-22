package torimia.superheroes.exceptions.handler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.util.JsonSerialization;
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
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
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
        return new ValidationResponse("Validation failed", data);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>("Entity with id: "
                + ex.getMessage().split(" ")[ex.getMessage().split(" ").length - 1]
                + " does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddingToListException.class)
    public ResponseEntity<String> handleAddingToList(AddingToListException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @SneakyThrows
    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<String> handleKeycloakClientError(ClientErrorException e) {
        if (e.getResponse().getStatus() == 409) {
            log.error("Keycloak error, user with current username or email  already exist. Message: " + e.getMessage());
            return new ResponseEntity<>("User with this username or email already exist!", HttpStatus.CONFLICT);
        } else {
            Response response = e.getResponse();
            Map error = JsonSerialization.readValue((ByteArrayInputStream) response.getEntity(), Map.class);
            log.error("Keycloak error description: " + error.get("error_description"));
            return new ResponseEntity<>("Oops, something wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }// TODO: 22.03.21 409 conflict ClientErrorException, при апдейті почти на існуючу в базі на серваку вилітає SQL exception

    }

    @ExceptionHandler(WebApplicationException.class)
    public ResponseEntity<String> handleKeycloakClientError(WebApplicationException e) {
        if (e.getResponse().getStatus() == 409) {
            log.error("Keycloak error, user with current username or email  already exist. Message: " + e.getMessage());
            return new ResponseEntity<>("User with this username or email already exist!", HttpStatus.CONFLICT);
        } else return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(e.getResponse().getStatus()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(e.getResponse().getStatus()));
    }
}


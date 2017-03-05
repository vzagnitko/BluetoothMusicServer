package ua.com.lsd25.controller.handler.validate;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.com.lsd25.controller.rest.ServerResponse;

import java.util.List;
import java.util.Map;

/**
 * @author vzagnitko
 */
@Log4j
@RestController
@ControllerAdvice
public class ValidationHandlingController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ServerResponse> conflict(ValidationException exc) {
        log.error(exc);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        List<FieldError> fieldErrors = (List<FieldError>) exc.getObjectErrors();
        Map<String, String> errorValidationFields = Maps.newHashMap();
        for (FieldError fieldError : fieldErrors) {
            errorValidationFields.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(new ServerResponse(errorValidationFields, status));
    }

}

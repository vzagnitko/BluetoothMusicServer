package ua.com.lsd25.controller.handler.validate;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.com.lsd25.controller.rest.ServerResponse;

import java.util.List;
import java.util.Map;

/**
 * @author vzagnitko
 */
@RestControllerAdvice
public class ValidationHandlingController {

    private static final Logger LOG = Logger.getLogger(ValidationHandlingController.class);

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ServerResponse> validationHandler(ValidationException exc) {
        LOG.error(exc);
        int status = HttpStatus.UNPROCESSABLE_ENTITY.value();
        List<FieldError> fieldErrors = (List<FieldError>) exc.getObjectErrors();
        Map<String, String> errorValidationFields = Maps.newHashMap();
        for (FieldError fieldError : fieldErrors) {
            errorValidationFields.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(new ServerResponse(errorValidationFields, status));
    }

}

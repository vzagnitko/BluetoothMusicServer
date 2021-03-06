package ua.com.lsd25.controller.handler.validate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author vzagnitko
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Validation problem")
public class ValidationException extends RuntimeException {

    private List<?> objectErrors;

    public ValidationException(List<?> objectErrors) {
        this.objectErrors = objectErrors;
    }

    public List<?> getObjectErrors() {
        return objectErrors;
    }

}

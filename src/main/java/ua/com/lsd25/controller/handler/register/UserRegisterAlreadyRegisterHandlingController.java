package ua.com.lsd25.controller.handler.register;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.com.lsd25.controller.rest.ServerResponse;

/**
 * @author vzagnitko
 */
@RestControllerAdvice
public class UserRegisterAlreadyRegisterHandlingController {

    private static final Logger LOG = Logger.getLogger(UserRegisterAlreadyRegisterHandlingController.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyRegisterException.class)
    public ResponseEntity<ServerResponse> validationHandler(UserAlreadyRegisterException exc) {
        LOG.error(exc);
        int status = HttpStatus.CONFLICT.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

}

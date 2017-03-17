package ua.com.lsd25.controller.handler.register;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.com.lsd25.controller.rest.ServerResponse;
import ua.com.lsd25.service.ApplicationException;

/**
 * @author vzagnitko
 */
@RestController
@ControllerAdvice
public class UserRegisterAlreadyRegisterHandlingController {

    private static final Logger LOG = Logger.getLogger(UserRegisterAlreadyRegisterHandlingController.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ServerResponse> validationHandler(ApplicationException exc) {
        LOG.error(exc);
        int status = HttpStatus.CONFLICT.value();
        return ResponseEntity.status(status).body(new ServerResponse(status));
    }

}

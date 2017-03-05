package ua.com.lsd25.controller.handler.user;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.com.lsd25.controller.rest.ServerResponse;

/**
 * @author vzagnitko
 */
@Log4j
@RestController
@ControllerAdvice
public class UserNotFoundHandlingController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ServerResponse> conflict(UsernameNotFoundException exc) {
        log.error(exc);
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

}

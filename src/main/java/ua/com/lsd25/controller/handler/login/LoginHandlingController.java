package ua.com.lsd25.controller.handler.login;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.com.lsd25.controller.rest.ServerResponse;

/**
 * @author vzagnitko
 */
@RestController
@ControllerAdvice
public class LoginHandlingController {

    private static final Logger LOG = Logger.getLogger(LoginHandlingController.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ServerResponse> notFoundHandler(UsernameNotFoundException exc) {
        LOG.error(exc);
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ServerResponse> badCredentials(BadCredentialsException exc) {
        LOG.error(exc);
        int status = HttpStatus.FORBIDDEN.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ServerResponse> badCredentials(DisabledException exc) {
        LOG.error(exc);
        int status = HttpStatus.FORBIDDEN.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyLoggedException.class)
    public ResponseEntity<ServerResponse> userAlreadyLogged(UserAlreadyLoggedException exc) {
        LOG.error(exc);
        int status = HttpStatus.CONFLICT.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServerResponse> userAlreadyLogged(HttpMessageNotReadableException exc) {
        LOG.error(exc);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ServerResponse> userAlreadyLogged(AccessDeniedException exc) {
        LOG.error(exc);
        int status = HttpStatus.FORBIDDEN.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

}

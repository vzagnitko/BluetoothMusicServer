package ua.com.lsd25.controller.handler.login;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.com.lsd25.controller.rest.ServerResponse;

/**
 * @author vzagnitko
 */
@RestControllerAdvice
public class LoginHandlingController {

    private static final Logger LOG = Logger.getLogger(LoginHandlingController.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ServerResponse> notFoundHandler(UsernameNotFoundException exc) {
        LOG.error(exc);
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ServerResponse> badCredentialsHandler(BadCredentialsException exc) {
        LOG.error(exc);
        int status = HttpStatus.UNAUTHORIZED.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ServerResponse> userDisabledHandler(DisabledException exc) {
        LOG.error(exc);
        int status = HttpStatus.UNAUTHORIZED.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyLoggedException.class)
    public ResponseEntity<ServerResponse> userAlreadyLoggedHandler(UserAlreadyLoggedException exc) {
        LOG.error(exc);
        int status = HttpStatus.CONFLICT.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServerResponse> cannotLoginHandler(HttpMessageNotReadableException exc) {
        LOG.error(exc);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ServerResponse> userAccessDeniedHandler(AccessDeniedException exc) {
        LOG.error(exc);
        int status = HttpStatus.FORBIDDEN.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ServerResponse> userLockedHandler(LockedException exc) {
        LOG.error(exc);
        int status = HttpStatus.UNAUTHORIZED.value();
        return ResponseEntity.status(status).body(new ServerResponse(exc.getLocalizedMessage(), status));
    }

}

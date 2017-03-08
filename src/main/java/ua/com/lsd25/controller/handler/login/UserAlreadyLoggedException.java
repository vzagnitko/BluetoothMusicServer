package ua.com.lsd25.controller.handler.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author vzagnitko
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User already logged in")
public class UserAlreadyLoggedException extends RuntimeException {

    private String message;

    public UserAlreadyLoggedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

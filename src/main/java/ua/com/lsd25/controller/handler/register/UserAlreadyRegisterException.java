package ua.com.lsd25.controller.handler.register;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author vzagnitko
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Registration problem")
public class UserAlreadyRegisterException extends RuntimeException {

    private String message;

    public UserAlreadyRegisterException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

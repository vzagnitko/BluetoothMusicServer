package ua.com.lsd25.controller.handler.register;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author vzagnitko
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Registration problem")
public class UserRegisterAlreadyRegisterException extends RuntimeException {
}

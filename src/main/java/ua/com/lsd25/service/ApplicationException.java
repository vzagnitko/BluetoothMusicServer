package ua.com.lsd25.service;

import lombok.Getter;

/**
 * @author vzagnitko
 */
public class ApplicationException extends Exception {

    @Getter
    private String message;

    public ApplicationException(Exception exception, String message) {
        super(message, exception);
    }

}

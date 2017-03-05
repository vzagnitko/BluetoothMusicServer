package ua.com.lsd25.repository;

import lombok.Getter;

/**
 * @author vzagnitko
 */
public class RepositoryException extends Exception {

    @Getter
    private String meessage;

    public RepositoryException(Exception exception, String message) {
        super(message, exception);
    }

}

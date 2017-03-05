package ua.com.lsd25.service;

/**
 * @author vzagnitko
 */
public class ApplicationException extends Exception {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Exception exception, String message) {
        super(message, exception);
    }

}

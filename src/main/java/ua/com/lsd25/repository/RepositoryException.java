package ua.com.lsd25.repository;

/**
 * @author vzagnitko
 */
public class RepositoryException extends Exception {

    public RepositoryException(Exception exception, String message) {
        super(message, exception);
    }

}

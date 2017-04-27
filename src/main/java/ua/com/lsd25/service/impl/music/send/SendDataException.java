package ua.com.lsd25.service.impl.music.send;

/**
 * @author vzagnitko
 */
public class SendDataException extends Exception {

    public SendDataException(String message) {
        super(message);
    }

    public SendDataException(Exception exception, String message) {
        super(message, exception);
    }

}

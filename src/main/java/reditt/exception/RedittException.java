package reditt.exception;

public class RedittException extends RuntimeException{

    public RedittException(String message, Exception exception) {
        super(message, exception);
    }

    public RedittException(String message) {
        super(message);
    }
}

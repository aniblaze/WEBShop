package hibernate;

/**
 * Represents Exceptions thrown by the Data Access Layer.
 */
public class DataAccessLayerException extends RuntimeException {
    public DataAccessLayerException() {
    }

    // Throws an exception with only the message
    public DataAccessLayerException(String message) {
        super(message);
    }
    // Throws an exception with only the cause
    public DataAccessLayerException(Throwable cause) {
        super(cause);
    }
    // Throws an exception with message & cause
    public DataAccessLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
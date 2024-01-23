package elocindev.customitemattributes.api;

public class InvalidAttributeException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidAttributeException(String message) {
        super(message);
    }
}

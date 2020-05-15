package businessLayer.Exceptions;

public class NotApprovedException extends RuntimeException {
    public NotApprovedException(String errorMessage) {
        super(errorMessage);
    }
}

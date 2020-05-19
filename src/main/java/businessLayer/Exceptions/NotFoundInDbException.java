package businessLayer.Exceptions;

public class NotFoundInDbException extends RuntimeException {
    public NotFoundInDbException(String errorMessage) {
        super(errorMessage);
    }
}
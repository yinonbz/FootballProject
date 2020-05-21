package businessLayer.Exceptions;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}

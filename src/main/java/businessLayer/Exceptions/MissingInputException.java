package businessLayer.Exceptions;

public class MissingInputException  extends RuntimeException {
    public MissingInputException(String errorMessage) {
        super(errorMessage);
    }
}

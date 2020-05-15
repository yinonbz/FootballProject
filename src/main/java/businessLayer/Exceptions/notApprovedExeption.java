package businessLayer.Exceptions;

public class notApprovedExeption extends RuntimeException {
    public notApprovedExeption(String errorMessage) {
        super(errorMessage);
    }
}

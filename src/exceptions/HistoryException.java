package exceptions;

public class HistoryException extends RuntimeException{
    public HistoryException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

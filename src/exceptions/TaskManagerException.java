package exceptions;

public class TaskManagerException extends RuntimeException{
    public TaskManagerException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

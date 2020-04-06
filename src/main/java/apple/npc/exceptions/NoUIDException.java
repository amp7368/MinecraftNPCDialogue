package apple.npc.exceptions;

public class NoUIDException extends Throwable {
    String message;

    public NoUIDException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

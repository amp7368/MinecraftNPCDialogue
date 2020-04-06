package apple.npc.exceptions;

public class BadUIDException extends Throwable {
    String message;

    public BadUIDException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

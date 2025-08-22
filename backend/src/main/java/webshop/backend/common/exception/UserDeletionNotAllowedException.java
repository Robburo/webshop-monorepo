package webshop.backend.common.exception;

public class UserDeletionNotAllowedException extends RuntimeException {
    public UserDeletionNotAllowedException(String message) {
        super(message);
    }
}

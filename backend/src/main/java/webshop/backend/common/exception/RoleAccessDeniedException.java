package webshop.backend.common.exception;

public class RoleAccessDeniedException extends RuntimeException {
    public RoleAccessDeniedException(String message) {
        super(message);
    }
}
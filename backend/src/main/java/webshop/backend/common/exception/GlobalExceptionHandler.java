package webshop.backend.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import webshop.backend.common.dto.ApiError;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ApiError buildError(HttpStatus status, String message, HttpServletRequest request) {
        return new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
    }

    // --- Specific domain not found exceptions ---
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        log.warn("UserNotFoundException at {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFound(ProductNotFoundException ex, HttpServletRequest request) {
        log.warn("ProductNotFoundException at {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFound(CategoryNotFoundException ex, HttpServletRequest request) {
        log.warn("CategoryNotFoundException at {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ApiError> handleCartItemNotFound(CartItemNotFoundException ex, HttpServletRequest request) {
        log.warn("CartItemNotFoundException at {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotFound(OrderNotFoundException ex, HttpServletRequest request) {
        log.warn("OrderNotFoundException at {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderItemNotFound(OrderItemNotFoundException ex, HttpServletRequest request) {
        log.warn("OrderItemNotFoundException at {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    // --- Validation errors ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("Validation error at {}: {}", request.getRequestURI(), message);
        return ResponseEntity.unprocessableEntity().body(buildError(HttpStatus.UNPROCESSABLE_ENTITY, message, request));
    }

    // --- Fallbacks ---
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex, HttpServletRequest request) {
        log.error("RuntimeException at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
    }

    @ExceptionHandler(UserDeletionNotAllowedException.class)
    public ResponseEntity<ApiError> HandleUserDeletionNotAllowed(UserDeletionNotAllowedException ex, HttpServletRequest request) {
        log.error("UserDeletionNotAllowedException at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Unhandled Exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request));
    }
}

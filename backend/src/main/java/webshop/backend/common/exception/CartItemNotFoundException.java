package webshop.backend.common.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long id) {
        super("Cart item with id " + id + " not found");
    }
}
package webshop.backend.domains.cart.dto;

public record CartItemDto(
        Long id,
        Long productId,
        String productName,
        int quantity,
        Long userId
) {}
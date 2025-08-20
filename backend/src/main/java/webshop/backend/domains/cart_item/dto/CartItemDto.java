package webshop.backend.domains.cart_item.dto;

public record CartItemDto(
        Long id,
        Long productId,
        String productName,
        int quantity,
        Long userId
) {}
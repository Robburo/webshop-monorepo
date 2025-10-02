package webshop.backend.domains.cart_item.dto;

import java.math.BigDecimal;

public record CartItemResponseDto(
        Long id,
        Long productId,
        String productName,
        int quantity,
        Long userId,
        BigDecimal price
) {}
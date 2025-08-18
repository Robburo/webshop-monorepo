package webshop.backend.domains.order.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        Long id,
        Long productId,
        String productName,
        int quantity,
        BigDecimal price
) {}

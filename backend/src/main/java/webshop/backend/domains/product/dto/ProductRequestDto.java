package webshop.backend.domains.product.dto;

import java.math.BigDecimal;

public record ProductRequestDto(
        String name,
        String description,
        BigDecimal price,
        int stock,
        Long categoryId
) {}
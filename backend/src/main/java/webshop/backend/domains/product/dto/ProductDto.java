package webshop.backend.domains.product.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stock,
        Long categoryId
) {}
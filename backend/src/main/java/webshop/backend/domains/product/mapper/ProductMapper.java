package webshop.backend.domains.product.mapper;

import webshop.backend.domains.product.Product;
import webshop.backend.domains.product.dto.ProductDto;

public class ProductMapper {
    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getId() : null
        );
    }
}
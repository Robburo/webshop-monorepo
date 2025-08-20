package webshop.backend.domains.product.mapper;

import webshop.backend.domains.category.Category;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.product.dto.ProductRequestDto;
import webshop.backend.domains.product.dto.ProductResponseDto;

public class ProductMapper {
    public static ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }

    public static Product toEntity(ProductRequestDto dto, Category category) {
        Product product = new Product();
        updateEntity(product, dto, category);
        return product;
    }

    public static void updateEntity(Product product, ProductRequestDto dto, Category category) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setCategory(category);
    }
}
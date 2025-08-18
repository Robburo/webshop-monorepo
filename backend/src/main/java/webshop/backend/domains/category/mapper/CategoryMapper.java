package webshop.backend.domains.category.mapper;

import webshop.backend.domains.category.Category;
import webshop.backend.domains.category.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}
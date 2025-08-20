package webshop.backend.domains.category.mapper;

import webshop.backend.domains.category.Category;
import webshop.backend.domains.category.dto.CategoryCreateDto;
import webshop.backend.domains.category.dto.CategoryResponseDto;

public class CategoryMapper {
    public static CategoryResponseDto toResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

    public static Category toEntity(CategoryCreateDto dto) {
        Category category = new Category();
        category.setName(dto.name());
        return category;
    }
}
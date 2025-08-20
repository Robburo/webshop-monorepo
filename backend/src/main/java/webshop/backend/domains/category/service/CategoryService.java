package webshop.backend.domains.category.service;

import org.springframework.stereotype.Service;
import webshop.backend.common.exception.CategoryNotFoundException;
import webshop.backend.domains.category.Category;
import webshop.backend.domains.category.dto.CategoryCreateDto;
import webshop.backend.domains.category.dto.CategoryResponseDto;
import webshop.backend.domains.category.mapper.CategoryMapper;
import webshop.backend.domains.category.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponseDto)
                .toList();
    }

    public CategoryResponseDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper::toResponseDto)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public CategoryResponseDto createCategory(CategoryCreateDto dto) {
        Category category = CategoryMapper.toEntity(dto);
        return CategoryMapper.toResponseDto(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
    }
}
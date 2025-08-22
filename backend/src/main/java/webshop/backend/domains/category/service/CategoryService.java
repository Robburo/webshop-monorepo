package webshop.backend.domains.category.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webshop.backend.common.exception.CategoryNotFoundException;
import webshop.backend.domains.category.Category;
import webshop.backend.domains.category.dto.CategoryCreateDto;
import webshop.backend.domains.category.dto.CategoryResponseDto;
import webshop.backend.domains.category.mapper.CategoryMapper;
import webshop.backend.domains.category.repository.CategoryRepository;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDto> getAllCategories() {
        log.debug("Fetching all categories");
        List<CategoryResponseDto> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponseDto)
                .toList();
        log.info("Fetched {} categories", categories.size());
        return categories;
    }

    public CategoryResponseDto getCategoryById(Long id) {
        log.debug("Fetching category by id={}", id);
        return categoryRepository.findById(id)
                .map(category -> {
                    log.info("Found category id={}", id);
                    return CategoryMapper.toResponseDto(category);
                })
                .orElseThrow(() -> {
                    log.warn("Category not found id={}", id);
                    return new CategoryNotFoundException(id);
                });
    }

    public CategoryResponseDto createCategory(CategoryCreateDto dto) {
        log.debug("Creating new category with name={}", dto.name());
        Category category = CategoryMapper.toEntity(dto);
        Category saved = categoryRepository.save(category);
        log.info("Created category id={} name={}", saved.getId(), saved.getName());
        return CategoryMapper.toResponseDto(saved);
    }

    public void deleteCategory(Long id) {
        log.debug("Deleting category id={}", id);
        if (!categoryRepository.existsById(id)) {
            log.warn("Category not found while deleting id={}", id);
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
        log.info("Deleted category id={}", id);
    }
}

package webshop.backend.domains.category.service;

import org.springframework.stereotype.Service;
import webshop.backend.domains.category.Category;
import webshop.backend.domains.category.dto.CategoryDto;
import webshop.backend.domains.category.mapper.CategoryMapper;
import webshop.backend.domains.category.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id).map(CategoryMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryDto createCategory(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.name());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
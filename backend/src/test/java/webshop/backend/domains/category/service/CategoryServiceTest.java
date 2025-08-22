package webshop.backend.domains.category.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webshop.backend.common.exception.CategoryNotFoundException;
import webshop.backend.domains.category.Category;
import webshop.backend.domains.category.dto.CategoryCreateDto;
import webshop.backend.domains.category.dto.CategoryResponseDto;
import webshop.backend.domains.category.mapper.CategoryMapper;
import webshop.backend.domains.category.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
    }

    @Test
    void getAllCategories_ShouldReturnList() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryResponseDto> result = categoryService.getAllCategories();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().name()).isEqualTo("Electronics");
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById_ShouldReturnCategory_WhenFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponseDto result = categoryService.getCategoryById(1L);

        assertThat(result.name()).isEqualTo("Electronics");
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void getCategoryById_ShouldThrow_WhenNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategoryById(99L))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    void createCategory_ShouldSaveAndReturnDto() {
        CategoryCreateDto dto = new CategoryCreateDto("Books");
        Category newCategory = CategoryMapper.toEntity(dto);
        newCategory.setId(2L);

        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        CategoryResponseDto result = categoryService.createCategory(dto);

        assertThat(result.name()).isEqualTo("Books");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void deleteCategory_ShouldDelete_WhenExists() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCategory_ShouldThrow_WhenNotExists() {
        when(categoryRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> categoryService.deleteCategory(99L))
                .isInstanceOf(CategoryNotFoundException.class);
    }
}

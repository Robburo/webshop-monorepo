package webshop.backend.domains.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webshop.backend.common.exception.CategoryNotFoundException;
import webshop.backend.common.exception.ProductNotFoundException;
import webshop.backend.domains.category.Category;
import webshop.backend.domains.category.repository.CategoryRepository;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.product.dto.ProductRequestDto;
import webshop.backend.domains.product.dto.ProductResponseDto;
import webshop.backend.domains.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;
    private ProductRequestDto requestDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(BigDecimal.valueOf(999.99));
        product.setStock(10);
        product.setCategory(category);

        requestDto = new ProductRequestDto("Laptop", "Gaming laptop", BigDecimal.valueOf(999.99), 10, 1L);
    }

    @Test
    @DisplayName("Should fetch all products")
    void getAllProducts_success() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDto> result = productService.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().name()).isEqualTo("Laptop");
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return product by id if found")
    void getProductById_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDto result = productService.getProductById(1L);

        assertThat(result.name()).isEqualTo("Laptop");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw when product not found by id")
    void getProductById_notFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("Should create product with valid category")
    void createProduct_success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDto result = productService.createProduct(requestDto);

        assertThat(result.name()).isEqualTo("Laptop");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should throw when category not found while creating product")
    void createProduct_categoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.createProduct(requestDto))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("Should update existing product")
    void updateProduct_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponseDto result = productService.updateProduct(1L, requestDto);

        assertThat(result.name()).isEqualTo("Laptop");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Should throw when updating non-existing product")
    void updateProduct_notFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(1L, requestDto))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("Should delete existing product")
    void deleteProduct_success() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw when deleting non-existing product")
    void deleteProduct_notFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }
}

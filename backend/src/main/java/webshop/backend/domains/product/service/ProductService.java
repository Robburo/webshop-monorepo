package webshop.backend.domains.product.service;

import org.springframework.stereotype.Service;
import webshop.backend.common.exception.CategoryNotFoundException;
import webshop.backend.common.exception.ProductNotFoundException;
import webshop.backend.domains.category.Category;
import webshop.backend.domains.category.repository.CategoryRepository;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.product.dto.ProductRequestDto;
import webshop.backend.domains.product.dto.ProductResponseDto;
import webshop.backend.domains.product.mapper.ProductMapper;
import webshop.backend.domains.product.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toResponseDto(product);
    }

    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Category category = null;
        if (dto.categoryId() != null) {
            category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));
        }
        Product product = ProductMapper.toEntity(dto, category);
        return ProductMapper.toResponseDto(productRepository.save(product));
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Category category = null;
        if (dto.categoryId() != null) {
            category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));
        }

        ProductMapper.updateEntity(product, dto, category);
        return ProductMapper.toResponseDto(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
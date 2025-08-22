package webshop.backend.domains.product.service;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponseDto> getAllProducts() {
        log.debug("Fetching all products");
        List<ProductResponseDto> products = productRepository.findAll().stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toList());
        log.info("Fetched {} products", products.size());
        return products;
    }

    public ProductResponseDto getProductById(Long id) {
        log.debug("Fetching product with id={}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with id={}", id);
                    return new ProductNotFoundException(id);
                });
        log.info("Product found with id={}", id);
        return ProductMapper.toResponseDto(product);
    }

    public ProductResponseDto createProduct(ProductRequestDto dto) {
        log.debug("Creating new product with name={} and categoryId={}", dto.name(), dto.categoryId());
        Category category = null;
        if (dto.categoryId() != null) {
            category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> {
                        log.warn("Category not found with id={} when creating product", dto.categoryId());
                        return new CategoryNotFoundException(dto.categoryId());
                    });
        }
        Product product = ProductMapper.toEntity(dto, category);
        Product saved = productRepository.save(product);
        log.info("Created new product with id={} and name={}", saved.getId(), saved.getName());
        return ProductMapper.toResponseDto(saved);
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        log.debug("Updating product with id={}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with id={} for update", id);
                    return new ProductNotFoundException(id);
                });

        Category category = null;
        if (dto.categoryId() != null) {
            category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> {
                        log.warn("Category not found with id={} when updating product id={}", dto.categoryId(), id);
                        return new CategoryNotFoundException(dto.categoryId());
                    });
        }

        ProductMapper.updateEntity(product, dto, category);
        Product updated = productRepository.save(product);
        log.info("Updated product with id={} and name={}", updated.getId(), updated.getName());
        return ProductMapper.toResponseDto(updated);
    }

    public void deleteProduct(Long id) {
        log.debug("Deleting product with id={}", id);
        if (!productRepository.existsById(id)) {
            log.warn("Product not found for deletion with id={}", id);
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        log.info("Deleted product with id={}", id);
    }
}

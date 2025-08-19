package webshop.backend.domains.product.service;

import org.springframework.stereotype.Service;
import webshop.backend.domains.category.Category;
import webshop.backend.domains.category.repository.CategoryRepository;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.product.dto.ProductDto;
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

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id).map(ProductMapper::toDto).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductDto createProduct(ProductDto dto) {
        Product product = new Product();
        return fillProductFields(dto, product);
    }

    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return fillProductFields(dto, product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    private ProductDto fillProductFields(ProductDto dto, Product product) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        return ProductMapper.toDto(productRepository.save(product));
    }
}
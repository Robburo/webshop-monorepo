package webshop.backend.domains.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.backend.domains.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
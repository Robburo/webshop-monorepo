package webshop.backend.domains.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.backend.domains.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

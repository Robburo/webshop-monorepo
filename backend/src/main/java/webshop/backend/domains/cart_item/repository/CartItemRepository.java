package webshop.backend.domains.cart_item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.backend.domains.cart_item.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
}
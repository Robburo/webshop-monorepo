package webshop.backend.domains.cart_item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.backend.domains.cart_item.CartItem;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);
}
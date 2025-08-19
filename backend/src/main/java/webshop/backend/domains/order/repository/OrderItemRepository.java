package webshop.backend.domains.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.backend.domains.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

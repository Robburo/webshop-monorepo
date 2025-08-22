package webshop.backend.domains.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.backend.domains.order.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}

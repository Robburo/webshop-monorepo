package webshop.backend.domains.order.service;

import org.springframework.stereotype.Service;
import webshop.backend.domains.order.dto.OrderItemDto;
import webshop.backend.domains.order.repository.OrderItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItemDto> getItemsByOrder(Long orderId) {
        return orderItemRepository.findByOrderId(orderId).stream()
                .map(item -> new OrderItemDto(
                        item.getId(),
                        item.getProduct() != null ? item.getProduct().getId() : null,
                        item.getProduct() != null ? item.getProduct().getName() : null,
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());
    }

    public OrderItemDto getItemById(Long id) {
        return orderItemRepository.findById(id)
                .map(item -> new OrderItemDto(
                        item.getId(),
                        item.getProduct() != null ? item.getProduct().getId() : null,
                        item.getProduct() != null ? item.getProduct().getName() : null,
                        item.getQuantity(),
                        item.getPrice()
                ))
                .orElseThrow(() -> new RuntimeException("Order item not found"));
    }
}
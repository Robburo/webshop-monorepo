package webshop.backend.domains.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webshop.backend.common.exception.OrderItemNotFoundException;
import webshop.backend.domains.order.dto.OrderItemDto;
import webshop.backend.domains.order.repository.OrderItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItemDto> getItemsByOrder(Long orderId) {
        log.debug("Fetching order items for orderId={}", orderId);
        List<OrderItemDto> items = orderItemRepository.findByOrderId(orderId).stream()
                .map(item -> new OrderItemDto(
                        item.getId(),
                        item.getProduct() != null ? item.getProduct().getId() : null,
                        item.getProduct() != null ? item.getProduct().getName() : null,
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());
        log.info("Fetched {} order items for orderId={}", items.size(), orderId);
        return items;
    }

    public OrderItemDto getItemById(Long id) {
        log.debug("Fetching order item with id={}", id);
        return orderItemRepository.findById(id)
                .map(item -> {
                    log.info("Order item found with id={}", id);
                    return new OrderItemDto(
                            item.getId(),
                            item.getProduct() != null ? item.getProduct().getId() : null,
                            item.getProduct() != null ? item.getProduct().getName() : null,
                            item.getQuantity(),
                            item.getPrice()
                    );
                })
                .orElseThrow(() -> {
                    log.warn("Order item not found with id={}", id);
                    return new OrderItemNotFoundException(id);
                });
    }
}

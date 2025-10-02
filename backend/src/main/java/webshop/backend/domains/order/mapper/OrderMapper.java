package webshop.backend.domains.order.mapper;

import webshop.backend.domains.order.Order;
import webshop.backend.domains.order.OrderItem;
import webshop.backend.domains.order.dto.OrderDto;
import webshop.backend.domains.order.dto.OrderItemDto;

import java.util.List;

public class OrderMapper {
    public static OrderDto toDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getUser() != null ? order.getUser().getId() : null,
                order.getCreatedAt(),
                order.getStatus(),
                order.getRecipientName(),
                order.getStreet(),
                order.getPostalCode(),
                order.getCity(),
                order.getCountry(),
                order.getItems() != null
                        ? order.getItems().stream()
                        .map(OrderMapper::toItemDto)
                        .toList()
                        : List.of()
        );
    }

    public static OrderItemDto toItemDto(OrderItem item) {
        return new OrderItemDto(
                item.getId(),
                item.getProduct() != null ? item.getProduct().getId() : null,
                item.getProduct() != null ? item.getProduct().getName() : null,
                item.getQuantity(),
                item.getPrice()
        );
    }
}
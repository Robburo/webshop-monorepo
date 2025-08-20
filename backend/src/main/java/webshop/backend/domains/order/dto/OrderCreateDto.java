package webshop.backend.domains.order.dto;

import java.util.List;

public record OrderCreateDto(List<OrderItemCreateDto> items) {
}
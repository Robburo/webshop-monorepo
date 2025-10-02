package webshop.backend.domains.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        Long userId,
        LocalDateTime createdAt,
        String status,
        String recipientName,
        String street,
        String postalCode,
        String city,
        String country,
        List<OrderItemDto> items
) {}
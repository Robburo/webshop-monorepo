package webshop.backend.domains.order.dto;

public record OrderItemCreateDto(Long productId, int quantity) {}
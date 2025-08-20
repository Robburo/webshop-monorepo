package webshop.backend.domains.cart_item.dto;

public record CartItemCreateDto(Long productId, int quantity) {
}
package webshop.backend.domains.cart_item.dto;

public record CartItemResponseDto(Long id, Long productId, String productName, int quantity, Long userId) {
}

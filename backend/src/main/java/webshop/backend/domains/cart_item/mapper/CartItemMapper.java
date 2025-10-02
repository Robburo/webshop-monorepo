package webshop.backend.domains.cart_item.mapper;

import webshop.backend.domains.cart_item.CartItem;
import webshop.backend.domains.cart_item.dto.CartItemResponseDto;

public class CartItemMapper {
    public static CartItemResponseDto toResponseDto(CartItem item) {
        return new CartItemResponseDto(
                item.getId(),
                item.getProduct() != null ? item.getProduct().getId() : null,
                item.getProduct() != null ? item.getProduct().getName() : null,
                item.getQuantity(),
                item.getUser() != null ? item.getUser().getId() : null,
                item.getProduct() != null ? item.getProduct().getPrice() : null
        );
    }
}
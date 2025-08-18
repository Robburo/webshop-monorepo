package webshop.backend.domains.cart.mapper;

import webshop.backend.domains.cart.CartItem;
import webshop.backend.domains.cart.dto.CartItemDto;

public class CartMapper {
    public static CartItemDto toDto(CartItem item) {
        return new CartItemDto(
                item.getId(),
                item.getProduct() != null ? item.getProduct().getId() : null,
                item.getProduct() != null ? item.getProduct().getName() : null,
                item.getQuantity(),
                item.getUser() != null ? item.getUser().getId() : null
        );
    }
}
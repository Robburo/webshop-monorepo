package webshop.backend.domains.cart_item.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webshop.backend.common.exception.CartItemNotFoundException;
import webshop.backend.domains.cart_item.CartItem;
import webshop.backend.domains.cart_item.dto.CartItemResponseDto;
import webshop.backend.domains.cart_item.repository.CartItemRepository;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.user.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartItemService cartItemService;

    private CartItem cartItem;

    @BeforeEach
    void init() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Product product = new Product();
        product.setId(2L);
        product.setName("Test Product");
        product.setPrice(java.math.BigDecimal.TEN);

        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
    }

    @Test
    void getAllCartItems_returnsList() {
        when(cartItemRepository.findAll()).thenReturn(List.of(cartItem));
        List<CartItemResponseDto> result = cartItemService.getAllCartItems();
        assertThat(result).hasSize(1);
    }

    @Test
    void updateCartItem_whenNotFound_throws() {
        when(cartItemRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cartItemService.updateCartItem(1L, 5))
                .isInstanceOf(CartItemNotFoundException.class);
    }

    @Test
    void updateCartItem_updatesQuantity() {
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        CartItemResponseDto result = cartItemService.updateCartItem(1L, 10);
        assertThat(result.quantity()).isEqualTo(10);
    }

    @Test
    void removeCartItem_whenNotExists_throws() {
        when(cartItemRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> cartItemService.removeCartItem(1L))
                .isInstanceOf(CartItemNotFoundException.class);
    }

    @Test
    void removeCartItem_deletes() {
        when(cartItemRepository.existsById(1L)).thenReturn(true);
        cartItemService.removeCartItem(1L);
        verify(cartItemRepository).deleteById(1L);
    }
}

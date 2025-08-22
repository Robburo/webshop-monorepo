package webshop.backend.domains.order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import webshop.backend.common.exception.EmptyCartException;
import webshop.backend.common.exception.UserNotFoundException;
import webshop.backend.domains.cart_item.CartItem;
import webshop.backend.domains.cart_item.repository.CartItemRepository;
import webshop.backend.domains.order.Order;
import webshop.backend.domains.order.dto.OrderDto;
import webshop.backend.domains.order.repository.OrderRepository;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.product.repository.ProductRepository;
import webshop.backend.domains.user.User;
import webshop.backend.domains.user.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private CartItemRepository cartItemRepository;
    @Mock private UserRepository userRepository;
    @Mock private ProductRepository productRepository;

    @Mock private Authentication authentication;
    @Mock private SecurityContext securityContext;

    @InjectMocks private OrderService orderService;

    @BeforeEach
    void setup() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void checkout_userNotFound_throwsException() {
        when(authentication.getName()).thenReturn("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.checkout())
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void checkout_emptyCart_throwsException() {
        User user = new User();
        user.setId(1L);
        when(authentication.getName()).thenReturn("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(cartItemRepository.findByUserId(1L)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> orderService.checkout())
                .isInstanceOf(EmptyCartException.class);
    }

    @Test
    void checkout_success() {
        User user = new User();
        user.setId(1L);
        when(authentication.getName()).thenReturn("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        Product product = new Product();
        product.setId(10L);
        product.setPrice(BigDecimal.valueOf(100));
        product.setStock(5);

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);

        when(cartItemRepository.findByUserId(1L)).thenReturn(Collections.singletonList(cartItem));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
            Order o = inv.getArgument(0);
            o.setId(200L);
            return o;
        });
        when(orderRepository.findById(200L)).thenReturn(Optional.of(new Order()));

        OrderDto dto = orderService.checkout();
        assertThat(dto).isNotNull();
        verify(productRepository).save(any(Product.class));
        verify(cartItemRepository).deleteAll(anyList());
    }

    @Test
    void getOrdersForCurrentUser_success() {
        User user = new User();
        user.setId(1L);
        when(authentication.getName()).thenReturn("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(orderRepository.findByUserId(1L)).thenReturn(Collections.singletonList(new Order()));

        assertThat(orderService.getOrdersForCurrentUser()).hasSize(1);
    }
}

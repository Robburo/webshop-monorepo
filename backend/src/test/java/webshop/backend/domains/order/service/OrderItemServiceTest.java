package webshop.backend.domains.order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import webshop.backend.common.exception.OrderItemNotFoundException;
import webshop.backend.domains.order.OrderItem;
import webshop.backend.domains.order.dto.OrderItemDto;
import webshop.backend.domains.order.repository.OrderItemRepository;
import webshop.backend.domains.product.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        orderItem = new OrderItem();
        orderItem.setId(10L);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setPrice(BigDecimal.valueOf(19.99));
    }

    @Test
    void getItemsByOrder_ShouldReturnOrderItems() {
        when(orderItemRepository.findByOrderId(100L)).thenReturn(List.of(orderItem));

        List<OrderItemDto> result = orderItemService.getItemsByOrder(100L);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().id()).isEqualTo(10L);
        assertThat(result.getFirst().productId()).isEqualTo(1L);
        assertThat(result.getFirst().productName()).isEqualTo("Test Product");
        assertThat(result.getFirst().quantity()).isEqualTo(2);
        assertThat(result.getFirst().price()).isEqualByComparingTo(BigDecimal.valueOf(19.99));

        verify(orderItemRepository, times(1)).findByOrderId(100L);
    }

    @Test
    void getItemById_ShouldReturnOrderItem_WhenExists() {
        when(orderItemRepository.findById(10L)).thenReturn(Optional.of(orderItem));

        OrderItemDto result = orderItemService.getItemById(10L);

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.productId()).isEqualTo(1L);
        assertThat(result.productName()).isEqualTo("Test Product");
        assertThat(result.quantity()).isEqualTo(2);
        assertThat(result.price()).isEqualByComparingTo(BigDecimal.valueOf(19.99));

        verify(orderItemRepository, times(1)).findById(10L);
    }

    @Test
    void getItemById_ShouldThrowException_WhenNotFound() {
        when(orderItemRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderItemService.getItemById(99L))
                .isInstanceOf(OrderItemNotFoundException.class)
                .hasMessageContaining("99");

        verify(orderItemRepository, times(1)).findById(99L);
    }
}

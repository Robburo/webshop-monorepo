package webshop.backend.domains.order.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import webshop.backend.common.exception.EmptyCartException;
import webshop.backend.common.exception.OrderNotFoundException;
import webshop.backend.common.exception.UserNotFoundException;
import webshop.backend.domains.cart_item.CartItem;
import webshop.backend.domains.cart_item.repository.CartItemRepository;
import webshop.backend.domains.order.Order;
import webshop.backend.domains.order.OrderItem;
import webshop.backend.domains.order.dto.OrderDto;
import webshop.backend.domains.order.mapper.OrderMapper;
import webshop.backend.domains.order.repository.OrderItemRepository;
import webshop.backend.domains.order.repository.OrderRepository;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.product.repository.ProductRepository;
import webshop.backend.domains.user.User;
import webshop.backend.domains.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CartItemRepository cartItemRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public OrderDto checkout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        if (cartItems.isEmpty()) {
            throw new EmptyCartException();
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        order = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            order.getItems().add(orderItem);

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        order = orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);

        Order finalOrder = order;
        return orderRepository.findById(order.getId())
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(finalOrder.getId()));
    }

    public List<OrderDto> getOrdersForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public OrderDto updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus(status);
        return OrderMapper.toDto(orderRepository.save(order));
    }
}
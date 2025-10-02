package webshop.backend.domains.order.service;

import lombok.extern.slf4j.Slf4j;
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
import webshop.backend.domains.order.repository.OrderRepository;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.product.repository.ProductRepository;
import webshop.backend.domains.user.User;
import webshop.backend.domains.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        CartItemRepository cartItemRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public OrderDto checkout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Checkout initiated for username={}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found during checkout: {}", username);
                    return new UserNotFoundException(username);
                });

        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        if (cartItems.isEmpty()) {
            log.warn("Empty cart for userId={}", user.getId());
            throw new EmptyCartException();
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        order = orderRepository.save(order);
        log.info("Created new order id={} for userId={}", order.getId(), user.getId());

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

            log.debug("Added orderItem productId={} qty={} to orderId={}",
                    product.getId(), cartItem.getQuantity(), order.getId());
        }

        order = orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);
        log.info("Checkout completed for orderId={} with {} items", order.getId(), order.getItems().size());

        Order finalOrder = order;
        return orderRepository.findById(order.getId())
                .map(OrderMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Order not found after checkout id={}", finalOrder.getId());
                    return new OrderNotFoundException(finalOrder.getId());
                });
    }

    public List<OrderDto> getOrdersForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Fetching orders for username={}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found while fetching orders: {}", username);
                    return new UserNotFoundException(username);
                });

        List<OrderDto> orders = orderRepository.findByUserId(user.getId())
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
        log.info("Fetched {} orders for userId={}", orders.size(), user.getId());

        return orders;
    }

    public OrderDto getOrderById(Long id) {
        log.debug("Fetching order by id={}", id);
        return orderRepository.findById(id)
                .map(order -> {
                    log.info("Found order id={}", id);
                    return OrderMapper.toDto(order);
                })
                .orElseThrow(() -> {
                    log.warn("Order not found with id={}", id);
                    return new OrderNotFoundException(id);
                });
    }

    public OrderDto updateOrderStatus(Long id, String status) {
        log.debug("Updating order status for orderId={} to {}", id, status);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Order not found while updating status id={}", id);
                    return new OrderNotFoundException(id);
                });

        order.setStatus(status);
        Order savedOrder = orderRepository.save(order);
        log.info("Updated orderId={} to status={}", id, status);

        return OrderMapper.toDto(savedOrder);
    }

    public List<OrderDto> getAllOrders() {
        log.debug("Fetching all orders (ADMIN)");
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public Object getSalesStatistics(String from, String to) {
        log.debug("Fetching sales statistics from={} to={} (ADMIN)", from, to);
        // TODO: Implementer faktisk statistikkberegning
        return null;
    }

    public Object getTopProducts() {
        log.debug("Fetching top products (ADMIN)");
        // TODO: Implementer logikk for å hente mest solgte produkter
        return null;
    }
}

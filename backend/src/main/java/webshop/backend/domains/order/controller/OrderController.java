package webshop.backend.domains.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.order.dto.OrderDto;
import webshop.backend.domains.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Endpoints for managing orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    @Operation(summary = "Checkout current user's cart", description = "Creates a new order from the current user's cart.")
    public ResponseEntity<OrderDto> checkout() {
        return ResponseEntity.ok(orderService.checkout());
    }

    @GetMapping
    @Operation(summary = "Get all orders for current user", description = "Retrieves a list of all orders placed by the current user.")
    public ResponseEntity<List<OrderDto>> getOrdersForCurrentUser() {
        return ResponseEntity.ok(orderService.getOrdersForCurrentUser());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieves details of a specific order by its ID.")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status", description = "Updates the status of an order (e.g., PENDING, PAID, SHIPPED).")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
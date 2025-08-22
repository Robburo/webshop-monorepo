package webshop.backend.domains.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.order.dto.OrderDto;
import webshop.backend.domains.order.service.OrderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Endpoints for managing orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Checkout and create a new order")
    @ApiResponse(responseCode = "200", description = "Order created successfully")
    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout() {
        log.debug("POST /api/orders/checkout called");
        return ResponseEntity.ok(orderService.checkout());
    }

    @Operation(summary = "Get all orders for the current user")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrdersForUser() {
        log.debug("GET /api/orders called");
        return ResponseEntity.ok(orderService.getOrdersForCurrentUser());
    }

    @Operation(summary = "Get an order by ID")
    @ApiResponse(responseCode = "200", description = "Order retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        log.debug("GET /api/orders/{} called", id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Update the status of an order")
    @ApiResponse(responseCode = "200", description = "Order status updated successfully")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        log.debug("PUT /api/orders/{}/status called with status={}", id, status);
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}

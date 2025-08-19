package webshop.backend.domains.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webshop.backend.domains.order.dto.OrderItemDto;
import webshop.backend.domains.order.service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@Tag(name = "Order Items", description = "Endpoints for managing order items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get items by order", description = "Retrieve all items belonging to a specific order")
    public ResponseEntity<List<OrderItemDto>> getItemsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.getItemsByOrder(orderId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order item by ID", description = "Retrieve a single order item by its ID")
    public ResponseEntity<OrderItemDto> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getItemById(id));
    }
}
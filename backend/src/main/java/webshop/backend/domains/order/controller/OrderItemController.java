package webshop.backend.domains.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webshop.backend.domains.order.dto.OrderItemDto;
import webshop.backend.domains.order.service.OrderItemService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order-items")
@Tag(name = "Order Items", description = "Endpoints for managing order items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Operation(summary = "Get all items by order ID")
    @ApiResponse(responseCode = "200", description = "Order items retrieved successfully")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDto>> getItemsByOrder(@PathVariable Long orderId) {
        log.debug("GET /api/order-items/order/{} called", orderId);
        return ResponseEntity.ok(orderItemService.getItemsByOrder(orderId));
    }

    @Operation(summary = "Get an order item by ID")
    @ApiResponse(responseCode = "200", description = "Order item retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> getItemById(@PathVariable Long id) {
        log.debug("GET /api/order-items/{} called", id);
        return ResponseEntity.ok(orderItemService.getItemById(id));
    }
}

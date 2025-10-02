package webshop.backend.domains.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.cart_item.dto.CartItemResponseDto;
import webshop.backend.domains.cart_item.service.CartItemService;
import webshop.backend.domains.order.dto.OrderDto;
import webshop.backend.domains.user.dto.UserResponseDto;
import webshop.backend.domains.user.service.UserService;
import webshop.backend.domains.order.dto.OrderResponseDto;
import webshop.backend.domains.order.service.OrderService;
import webshop.backend.domains.product.dto.ProductRequestDto;
import webshop.backend.domains.product.dto.ProductResponseDto;
import webshop.backend.domains.product.service.ProductService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;
    private final CartItemService cartItemService;
    private final OrderService orderService;
    private final ProductService productService;

    public AdminController(UserService userService,
                           CartItemService cartItemService,
                           OrderService orderService,
                           ProductService productService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.orderService = orderService;
        this.productService = productService;
    }

    // --- USER MANAGEMENT ---

    @Operation(summary = "Get all users", description = "Accessible only for ADMIN role")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.debug("GET /api/admin/users called by ADMIN");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get user by id", description = "Accessible only for ADMIN role")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        log.debug("GET /api/admin/users/{} called by ADMIN", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Update user", description = "Update user role, status, or profile (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @RequestBody UserResponseDto dto) {
        log.debug("PUT /api/admin/users/{} called by ADMIN", id);
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @Operation(summary = "Delete user", description = "Delete a user by ID (ADMIN only)")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("DELETE /api/admin/users/{} called by ADMIN", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // --- CART ITEMS ---

    @Operation(summary = "Get all cart items", description = "Get cart items for all users (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Cart items retrieved successfully")
    @GetMapping("/cart_items")
    public ResponseEntity<List<CartItemResponseDto>> getAllCartItems() {
        log.debug("GET /api/admin/cart_items called by ADMIN");
        return ResponseEntity.ok(cartItemService.getAllCartItems());
    }

    // --- ORDERS ---

    @Operation(summary = "Get all orders", description = "Get all orders in the system (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        log.debug("GET /api/admin/orders called by ADMIN");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Update order status", description = "Update status of an order (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Order status updated successfully")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PutMapping("/orders/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long id,
                                                              @RequestParam String status) {
        log.debug("PUT /api/admin/orders/{}/status called by ADMIN with status={}", id, status);
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    // --- PRODUCT MANAGEMENT ---

    @Operation(summary = "Create new product", description = "Create a new product (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Product created successfully")
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto dto) {
        log.debug("POST /api/admin/products called by ADMIN");
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @Operation(summary = "Update product", description = "Update an existing product (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
                                                            @RequestBody ProductRequestDto dto) {
        log.debug("PUT /api/admin/products/{} called by ADMIN", id);
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @Operation(summary = "Delete product", description = "Delete a product by ID (ADMIN only)")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("DELETE /api/admin/products/{} called by ADMIN", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update product stock", description = "Update stock level of a product (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Product stock updated successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @PatchMapping("/products/{id}/stock")
    public ResponseEntity<ProductResponseDto> updateProductStock(@PathVariable Long id,
                                                                 @RequestParam int stock) {
        log.debug("PATCH /api/admin/products/{}/stock called by ADMIN with stock={}", id, stock);
        return ResponseEntity.ok(productService.updateProductStock(id, stock));
    }

    // --- STATISTICS ---

    @Operation(summary = "Get sales statistics", description = "Get sales statistics between two dates (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Sales statistics retrieved successfully")
    @GetMapping("/statistics/sales")
    public ResponseEntity<?> getSalesStatistics(@RequestParam String from,
                                                @RequestParam String to) {
        log.debug("GET /api/admin/statistics/sales from={} to={} called by ADMIN", from, to);
        return ResponseEntity.ok(orderService.getSalesStatistics(from, to));
    }

    @Operation(summary = "Get top products", description = "Get top selling products (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Top products retrieved successfully")
    @GetMapping("/statistics/top-products")
    public ResponseEntity<?> getTopProducts() {
        log.debug("GET /api/admin/statistics/top-products called by ADMIN");
        return ResponseEntity.ok(orderService.getTopProducts());
    }
}

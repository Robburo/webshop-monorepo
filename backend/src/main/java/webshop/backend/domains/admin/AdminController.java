package webshop.backend.domains.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.cart_item.dto.CartItemResponseDto;
import webshop.backend.domains.cart_item.service.CartItemService;
import webshop.backend.domains.user.dto.UserResponseDto;
import webshop.backend.domains.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;
    private final CartItemService cartItemService;

    public AdminController(UserService userService, CartItemService cartItemService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
    }

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

    @Operation(summary = "Delete user", description = "Delete a user by ID (ADMIN only)")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("DELETE /api/admin/users/{} called by ADMIN", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all cart items", description = "Get cart items for all users")
    @ApiResponse(responseCode = "200", description = "Cart items retrieved successfully")
    @GetMapping("/cart_items")
    public ResponseEntity<List<CartItemResponseDto>> getAllCartItems() {
        log.debug("GET /api/admin/cart_items called by ADMIN");
        return ResponseEntity.ok(cartItemService.getAllCartItems());
    }
}

package webshop.backend.domains.cart_item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.cart_item.dto.CartItemCreateDto;
import webshop.backend.domains.cart_item.dto.CartItemResponseDto;
import webshop.backend.domains.cart_item.service.CartItemService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Cart management APIs")
public class CartItemController {

    private final CartItemService service;

    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @Operation(summary = "Get cart items for current user")
    @ApiResponse(responseCode = "200", description = "List of cart items")
    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getCartItems() {
        log.debug("GET /api/cart called");
        return ResponseEntity.ok(service.getCartItemsForCurrentUser());
    }

    @Operation(summary = "Add item to cart")
    @ApiResponse(responseCode = "200", description = "Item added to cart")
    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDto> addToCart(@RequestBody CartItemCreateDto dto) {
        log.debug("POST /api/cart/add called with payload: {}", dto);
        return ResponseEntity.ok(service.addToCart(dto));
    }

    @Operation(summary = "Update cart item quantity")
    @ApiResponse(responseCode = "200", description = "Cart item updated")
    @PutMapping("/{itemId}")
    public ResponseEntity<CartItemResponseDto> updateCartItem(@PathVariable Long itemId, @RequestParam int quantity) {
        log.debug("PUT /api/cart/{} called with quantity: {}", itemId, quantity);
        return ResponseEntity.ok(service.updateCartItem(itemId, quantity));
    }

    @Operation(summary = "Remove cart item")
    @ApiResponse(responseCode = "204", description = "Cart item removed")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long itemId) {
        log.debug("DELETE /api/cart/{} called", itemId);
        service.removeCartItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Clear cart")
    @ApiResponse(responseCode = "204", description = "Cart cleared")
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        log.debug("DELETE /api/cart/clear called");
        service.clearCart();
        return ResponseEntity.noContent().build();
    }
}

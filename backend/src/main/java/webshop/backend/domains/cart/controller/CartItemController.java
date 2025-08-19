package webshop.backend.domains.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.cart.dto.CartItemDto;
import webshop.backend.domains.cart.service.CartItemService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Cart management APIs")
public class CartItemController {

    private final CartItemService service;

    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all cart items for current user")
    public ResponseEntity<List<CartItemDto>> getCartItems() {
        return ResponseEntity.ok(service.getCartItemsForCurrentUser());
    }

    @PostMapping("/add")
    @Operation(summary = "Add product to cart")
    public ResponseEntity<CartItemDto> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(service.addToCart(productId, quantity));
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable Long itemId, @RequestParam int quantity) {
        return ResponseEntity.ok(service.updateCartItem(itemId, quantity));
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long itemId) {
        service.removeCartItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Clear all items from cart")
    public ResponseEntity<Void> clearCart() {
        service.clearCart();
        return ResponseEntity.noContent().build();
    }
}
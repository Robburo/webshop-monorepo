package webshop.backend.domains.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.cart.dto.CartItemDto;
import webshop.backend.domains.cart.service.CartItemService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService service;

    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCartItems() {
        return ResponseEntity.ok(service.getCartItemsForCurrentUser());
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(service.addToCart(productId, quantity));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable Long itemId, @RequestParam int quantity) {
        return ResponseEntity.ok(service.updateCartItem(itemId, quantity));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long itemId) {
        service.removeCartItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        service.clearCart();
        return ResponseEntity.noContent().build();
    }
}
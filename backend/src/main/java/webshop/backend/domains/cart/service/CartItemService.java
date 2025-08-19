package webshop.backend.domains.cart.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import webshop.backend.domains.cart.CartItem;
import webshop.backend.domains.cart.dto.CartItemDto;
import webshop.backend.domains.cart.mapper.CartMapper;
import webshop.backend.domains.cart.repository.CartItemRepository;
import webshop.backend.domains.product.Product;
import webshop.backend.domains.product.repository.ProductRepository;
import webshop.backend.domains.user.User;
import webshop.backend.domains.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<CartItemDto> getCartItemsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartItemRepository.findByUserId(user.getId()).stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());
    }

    public CartItemDto addToCart(Long productId, int quantity) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        return CartMapper.toDto(cartItemRepository.save(cartItem));
    }

    public CartItemDto updateCartItem(Long itemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItem.setQuantity(quantity);
        return CartMapper.toDto(cartItemRepository.save(cartItem));
    }

    public void removeCartItem(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            throw new RuntimeException("Cart item not found");
        }
        cartItemRepository.deleteById(itemId);
    }

    public void clearCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<CartItem> items = cartItemRepository.findByUserId(user.getId());
        cartItemRepository.deleteAll(items);
    }
}
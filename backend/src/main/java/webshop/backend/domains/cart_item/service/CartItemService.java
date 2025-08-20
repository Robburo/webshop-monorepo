package webshop.backend.domains.cart_item.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import webshop.backend.common.exception.CartItemNotFoundException;
import webshop.backend.common.exception.ProductNotFoundException;
import webshop.backend.common.exception.UserNotFoundException;
import webshop.backend.domains.cart_item.CartItem;
import webshop.backend.domains.cart_item.dto.CartItemCreateDto;
import webshop.backend.domains.cart_item.dto.CartItemResponseDto;
import webshop.backend.domains.cart_item.mapper.CartItemMapper;
import webshop.backend.domains.cart_item.repository.CartItemRepository;
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

    public List<CartItemResponseDto> getCartItemsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return cartItemRepository.findByUserId(user.getId())
                .stream()
                .map(CartItemMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public CartItemResponseDto addToCart(CartItemCreateDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ProductNotFoundException(dto.productId()));

        CartItem item = new CartItem();
        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(dto.quantity());

        return CartItemMapper.toResponseDto(cartItemRepository.save(item));
    }

    public CartItemResponseDto updateCartItem(Long itemId, int quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new CartItemNotFoundException(itemId));

        item.setQuantity(quantity);
        return CartItemMapper.toResponseDto(cartItemRepository.save(item));
    }

    public void removeCartItem(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            throw new CartItemNotFoundException(itemId);
        }
        cartItemRepository.deleteById(itemId);
    }

    public void clearCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        cartItemRepository.deleteAll(cartItems);
    }
}
package webshop.backend.domains.cart_item.service;

import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    public List<CartItemResponseDto> getAllCartItems() {
        log.debug("Fetching all cart items");
        List<CartItemResponseDto> items = cartItemRepository.findAll()
                .stream()
                .map(CartItemMapper::toResponseDto)
                .collect(Collectors.toList());
        log.info("Fetched {} cart items", items.size());
        return items;
    }

    public List<CartItemResponseDto> getCartItemsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Fetching cart items for current user username={}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found while fetching cart items username={}", username);
                    return new UserNotFoundException(username);
                });

        List<CartItemResponseDto> items = cartItemRepository.findByUserId(user.getId())
                .stream()
                .map(CartItemMapper::toResponseDto)
                .collect(Collectors.toList());

        log.info("Fetched {} cart items for userId={}", items.size(), user.getId());
        return items;
    }

    public CartItemResponseDto addToCart(CartItemCreateDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Adding productId={} quantity={} to cart for username={}", dto.productId(), dto.quantity(), username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found while adding to cart username={}", username);
                    return new UserNotFoundException(username);
                });

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> {
                    log.warn("Product not found productId={} while adding to cart", dto.productId());
                    return new ProductNotFoundException(dto.productId());
                });

        // Sjekk om varen allerede finnes i cart
        Optional<CartItem> existing = cartItemRepository.findByUserIdAndProductId(user.getId(), product.getId());

        CartItem item;
        if (existing.isPresent()) {
            item = existing.get();
            item.setQuantity(item.getQuantity() + dto.quantity());
            log.info("Increased quantity for productId={} in cart for userId={} to {}",
                    product.getId(), user.getId(), item.getQuantity());
        } else {
            item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(dto.quantity());
            log.info("Added new productId={} quantity={} to cart for userId={}",
                    product.getId(), dto.quantity(), user.getId());
        }

        CartItem saved = cartItemRepository.save(item);
        return CartItemMapper.toResponseDto(saved);
    }

    public CartItemResponseDto updateCartItem(Long itemId, int quantity) {
        log.debug("Updating cartItemId={} to quantity={}", itemId, quantity);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.warn("Cart item not found cartItemId={}", itemId);
                    return new CartItemNotFoundException(itemId);
                });

        item.setQuantity(quantity);
        CartItem updated = cartItemRepository.save(item);
        log.info("Updated cartItemId={} to quantity={}", updated.getId(), updated.getQuantity());
        return CartItemMapper.toResponseDto(updated);
    }

    public void removeCartItem(Long itemId) {
        log.debug("Removing cartItemId={}", itemId);

        if (!cartItemRepository.existsById(itemId)) {
            log.warn("Cart item not found while removing cartItemId={}", itemId);
            throw new CartItemNotFoundException(itemId);
        }
        cartItemRepository.deleteById(itemId);
        log.info("Removed cartItemId={}", itemId);
    }

    public void clearCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Clearing cart for username={}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found while clearing cart username={}", username);
                    return new UserNotFoundException(username);
                });

        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        cartItemRepository.deleteAll(cartItems);
        log.info("Cleared {} cart items for userId={}", cartItems.size(), user.getId());
    }
}

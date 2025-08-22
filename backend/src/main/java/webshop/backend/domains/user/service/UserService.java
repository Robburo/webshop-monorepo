package webshop.backend.domains.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webshop.backend.common.exception.UserDeletionNotAllowedException;
import webshop.backend.common.exception.UserNotFoundException;
import webshop.backend.domains.cart_item.repository.CartItemRepository;
import webshop.backend.domains.order.repository.OrderRepository;
import webshop.backend.domains.user.User;
import webshop.backend.domains.user.dto.UserRequestDto;
import webshop.backend.domains.user.dto.UserResponseDto;
import webshop.backend.domains.user.mapper.UserMapper;
import webshop.backend.domains.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartItemRepository cartItemRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
    }

    public UserResponseDto getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Fetching current user with username={}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", username);
                    return new UserNotFoundException("User not found: " + username);
                });
        log.info("Successfully fetched current user with id={}", user.getId());
        return UserMapper.toResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        log.debug("Fetching all users");
        List<UserResponseDto> users = userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
        log.info("Fetched {} users", users.size());
        return users;
    }

    public UserResponseDto getUserById(Long id) {
        log.debug("Fetching user with id={}", id);
        return userRepository.findById(id)
                .map(user -> {
                    log.info("User found with id={}", id);
                    return UserMapper.toResponseDto(user);
                })
                .orElseThrow(() -> {
                    log.warn("User not found with id={}", id);
                    return new UserNotFoundException("User not found with id: " + id);
                });
    }

    public UserResponseDto registerUser(UserRequestDto dto) {
        log.debug("Registering new user with username={}", dto.username());
        User user = UserMapper.toEntity(dto, passwordEncoder);
        user.setPassword(passwordEncoder.encode(dto.password()));
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }
        User saved = userRepository.save(user);
        log.info("Registered new user with id={} and username={}", saved.getId(), saved.getUsername());
        return UserMapper.toResponseDto(saved);
    }

    public void deleteUser(Long id) {
        log.debug("Deleting user with id={}", id);

        var user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for deletion with id={}", id);
                    return new UserNotFoundException("User not found with id " + id);
                });

        // Check for existing references before deleting
        boolean hasCartItems = cartItemRepository.existsByUserId(id);
        if (hasCartItems) {
            log.warn("Cannot delete user with id={} because it still has cart items", id);
            throw new UserDeletionNotAllowedException("Cannot delete user with id=" + id + " because it has related cart items");
        }

        boolean hasOrders = orderRepository.existsByUserId(id);
        if (hasOrders) {
            log.warn("Cannot delete user with id={} because it still has orders", id);
            throw new UserDeletionNotAllowedException("Cannot delete user with id=" + id + " because it has related orders");
        }

        userRepository.delete(user);
        log.info("Deleted user with id={}", id);
    }
}

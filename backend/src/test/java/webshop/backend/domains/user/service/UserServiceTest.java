package webshop.backend.domains.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import webshop.backend.common.exception.UserNotFoundException;
import webshop.backend.domains.user.User;
import webshop.backend.domains.user.dto.UserRequestDto;
import webshop.backend.domains.user.dto.UserResponseDto;
import webshop.backend.domains.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return current user when authenticated user exists")
    void getCurrentUser_success() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Act
        UserResponseDto response = userService.getCurrentUser();

        // Assert
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.username()).isEqualTo("testuser");
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    @DisplayName("should throw UserNotFoundException when current user does not exist")
    void getCurrentUser_notFound() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("missinguser");
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        when(userRepository.findByUsername("missinguser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.getCurrentUser())
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("missinguser");
    }

    @Test
    @DisplayName("should fetch all users successfully")
    void getAllUsers_success() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("u1");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("u2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<UserResponseDto> users = userService.getAllUsers();

        // Assert
        assertThat(users).hasSize(2);
        assertThat(users).extracting("username").containsExactlyInAnyOrder("u1", "u2");
    }

    @Test
    @DisplayName("should get user by id when exists")
    void getUserById_success() {
        // Arrange
        User user = new User();
        user.setId(5L);
        user.setUsername("findme");

        when(userRepository.findById(5L)).thenReturn(Optional.of(user));

        // Act
        UserResponseDto response = userService.getUserById(5L);

        // Assert
        assertThat(response.id()).isEqualTo(5L);
        assertThat(response.username()).isEqualTo("findme");
    }

    @Test
    @DisplayName("should throw UserNotFoundException when user by id does not exist")
    void getUserById_notFound() {
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(10L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("10");
    }

    @Test
    @DisplayName("should register new user with default role")
    void registerUser_success() {
        // Arrange
        UserRequestDto dto = new UserRequestDto("newuser", "password", null, "ROLE_USER");

        User saved = new User();
        saved.setId(99L);
        saved.setUsername("newuser");
        saved.setRole("ROLE_USER");

        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(saved);

        // Act
        UserResponseDto response = userService.registerUser(dto);

        // Assert
        assertThat(response.id()).isEqualTo(99L);
        assertThat(response.username()).isEqualTo("newuser");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("should delete user when exists")
    void deleteUser_success() {
        User user = new User();
        user.setId(20L);

        when(userRepository.findById(20L)).thenReturn(Optional.of(user));

        userService.deleteUser(20L);

        verify(userRepository).deleteById(20L);
    }

    @Test
    @DisplayName("should throw UserNotFoundException when deleting non-existing user")
    void deleteUser_notFound() {
        when(userRepository.findById(123L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser(123L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("123");
    }
}

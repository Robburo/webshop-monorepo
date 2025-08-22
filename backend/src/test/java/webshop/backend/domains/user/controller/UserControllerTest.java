package webshop.backend.domains.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import webshop.backend.domains.user.dto.UserRequestDto;
import webshop.backend.domains.user.dto.UserResponseDto;
import webshop.backend.domains.user.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserResponseDto userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userResponse = new UserResponseDto(1L, "testuser", "test@example.com", "ROLE_USER");
    }

    @Test
    void getCurrentUser_returnsUser() {
        when(userService.getCurrentUser()).thenReturn(userResponse);

        ResponseEntity<UserResponseDto> response = userController.getCurrentUser();

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(userResponse);
    }

    @Test
    void getAllUsers_returnsList() {
        when(userService.getAllUsers()).thenReturn(List.of(userResponse));

        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().getFirst()).isEqualTo(userResponse);
    }

    @Test
    void getUserById_returnsUser() {
        when(userService.getUserById(1L)).thenReturn(userResponse);

        ResponseEntity<UserResponseDto> response = userController.getUserById(1L);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(userResponse);
    }

    @Test
    void registerUser_returnsCreatedUser() {
        UserRequestDto request = new UserRequestDto("newuser", "new@example.com", "password", "ROLE_USER");
        when(userService.registerUser(any(UserRequestDto.class))).thenReturn(userResponse);

        ResponseEntity<UserResponseDto> response = userController.registerUser(request);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(userResponse);
    }
}

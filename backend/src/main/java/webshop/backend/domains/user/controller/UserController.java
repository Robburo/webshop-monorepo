package webshop.backend.domains.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.user.dto.UserRequestDto;
import webshop.backend.domains.user.dto.UserResponseDto;
import webshop.backend.domains.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for user management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current logged-in user", description = "Fetch details of the currently authenticated user")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their unique ID")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user with a username, email, role, and password")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }
}
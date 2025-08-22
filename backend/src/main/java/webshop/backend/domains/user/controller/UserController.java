package webshop.backend.domains.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.backend.domains.user.dto.UserRequestDto;
import webshop.backend.domains.user.dto.UserResponseDto;
import webshop.backend.domains.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Fetch details of the currently authenticated user")
    @ApiResponse(responseCode = "200", description = "Current user details returned successfully")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        log.debug("GET /api/users/me called");
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Fetch a list of all registered users")
    @ApiResponse(responseCode = "200", description = "List of users returned successfully")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.debug("GET /api/users called");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Fetch user details by their ID")
    @ApiResponse(responseCode = "200", description = "User details returned successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        log.debug("GET /api/users/{} called", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Register a new user with username, email, and password")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userDto) {
        log.debug("POST /api/users/register called");
        return ResponseEntity.ok(userService.registerUser(userDto));
    }
}

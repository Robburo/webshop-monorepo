package webshop.backend.domains.user.dto;

public record UserResponseDto(
        Long id,
        String username,
        String email,
        String role
) {}
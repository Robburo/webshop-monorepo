package webshop.backend.domains.user.dto;

public record UserDto(
        Long id,
        String username,
        String email,
        String role
) {}
package webshop.backend.domains.user.dto;

public record UserRequestDto(
        String username,
        String email,
        String password,
        String role
) {}
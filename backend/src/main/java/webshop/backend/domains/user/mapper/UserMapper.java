package webshop.backend.domains.user.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import webshop.backend.domains.user.User;
import webshop.backend.domains.user.dto.UserRequestDto;
import webshop.backend.domains.user.dto.UserResponseDto;
import webshop.backend.domains.user.enums.UserRole;

public class UserMapper {

    public static UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    public static User toEntity(UserRequestDto dto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(dto.username().toLowerCase());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        if (dto.role() != null) {
            try {
                user.setRole(UserRole.valueOf(dto.role()));
            } catch (IllegalArgumentException e) {
                user.setRole(UserRole.ROLE_USER); // fallback
            }
        } else {
            user.setRole(UserRole.ROLE_USER);
        }

        return user;
    }
}
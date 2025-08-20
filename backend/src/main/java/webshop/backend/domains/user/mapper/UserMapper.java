package webshop.backend.domains.user.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import webshop.backend.domains.user.User;
import webshop.backend.domains.user.dto.UserRequestDto;
import webshop.backend.domains.user.dto.UserResponseDto;

public class UserMapper {
    public static UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static User toEntity(UserRequestDto dto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role() != null ? dto.role() : "ROLE_USER");
        return user;
    }
}
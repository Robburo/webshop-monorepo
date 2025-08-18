package webshop.backend.domains.user.mapper;

import webshop.backend.domains.user.User;
import webshop.backend.domains.user.dto.UserDto;

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
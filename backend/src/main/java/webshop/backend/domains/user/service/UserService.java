package webshop.backend.domains.user.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webshop.backend.domains.user.User;
import webshop.backend.domains.user.dto.UserRequestDto;
import webshop.backend.domains.user.dto.UserResponseDto;
import webshop.backend.domains.user.mapper.UserMapper;
import webshop.backend.domains.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponseDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDto registerUser(UserRequestDto dto) {
        User user = UserMapper.toEntity(dto, passwordEncoder);
        user.setPassword(passwordEncoder.encode(dto.password()));
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }
        return UserMapper.toResponseDto(userRepository.save(user));
    }
}
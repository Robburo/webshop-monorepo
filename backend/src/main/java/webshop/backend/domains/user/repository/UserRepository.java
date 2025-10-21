package webshop.backend.domains.user.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import webshop.backend.domains.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameIgnoreCase(@NotBlank @Size(min = 3, max = 50) String username);

    boolean existsByEmailIgnoreCase(@NotBlank @Size(min = 3, max = 50) String email);
}
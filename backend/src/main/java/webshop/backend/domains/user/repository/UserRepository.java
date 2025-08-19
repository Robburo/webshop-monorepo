package webshop.backend.domains.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.backend.domains.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
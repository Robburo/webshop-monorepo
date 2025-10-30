package webshop.backend.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webshop.backend.auth.dto.RefreshTokenRequest;
import webshop.backend.auth.dto.TokenPairResponse;
import webshop.backend.auth.dto.TokenResponse;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenPairResponse> token(Authentication authentication) {
        log.info("POST /api/auth/token called");

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Authentication failed or missing");
            throw new RuntimeException("Invalid login");
        }

        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String jwt = jwtService.generateToken(username, roles);
        String refreshToken = jwtService.generateRefreshToken(username, roles);
        return ResponseEntity.ok(new TokenPairResponse(jwt, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        log.info("POST /api/auth/refresh called");

        try {
            // Parse and validate refresh token
            var claims = jwtService.parseClaims(request.refreshToken());
            if (!jwtService.isRefreshToken(request.refreshToken())) {
                log.warn("Invalid token type");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new TokenResponse("Invalid refresh token type"));
            }

            String username = claims.getSubject();
            @SuppressWarnings("unchecked")
            List<String> roles = claims.get("roles", List.class);

            // Generate new access token
            String newAccessToken = jwtService.generateToken(username, roles);

            log.info("Access token refreshed for user '{}'", username);
            return ResponseEntity.ok(new TokenResponse(newAccessToken));

        } catch (Exception e) {
            log.warn("Token refresh failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenResponse("Invalid or expired refresh token"));
        }
    }
}

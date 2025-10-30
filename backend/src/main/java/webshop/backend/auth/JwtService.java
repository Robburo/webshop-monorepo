package webshop.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private final Key key;
    private final long expiration;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
        log.info("JwtService initialized with expiration={}ms", expiration);
    }

    public String generateToken(String username, List<String> roles) {
        log.info("Generating JWT for user '{}' with roles {}", username, roles);

        String token = Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();

        log.debug("Generated token for user '{}' with roles {}", username, roles);
        return token;
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateRefreshToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("type", "refresh")
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 24))
                .signWith(key)
                .compact();
    }

    public boolean isRefreshToken(String token) {
        Claims claims = parseClaims(token);
        String type = claims.get("type", String.class);
        return "refresh".equals(type);
    }
}

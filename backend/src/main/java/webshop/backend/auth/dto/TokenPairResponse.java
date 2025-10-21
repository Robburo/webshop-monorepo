package webshop.backend.auth.dto;

public record TokenPairResponse(String token, String refreshToken) {
}

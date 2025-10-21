package webshop.backend.config;

import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IP-based rate limiting for open API endpoints using Bucket4j.
 */
@Component
public class RateLimitFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RateLimitFilter.class);
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(50) // maximum number of tokens
                        .refillIntervally(50, Duration.ofMinutes(1)) // tokens per minute
                )
                .build();
    }

    private boolean isProtectedEndpoint(String path) {
        return path.startsWith("/api/products")
                || path.startsWith("/api/categories")
                || path.equals("/api/users/register");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();

        if (isProtectedEndpoint(path)) {
            String ip = httpRequest.getRemoteAddr();
            Bucket bucket = buckets.computeIfAbsent(ip, k -> createNewBucket());

            if (!bucket.tryConsume(1)) {
                log.warn("Rate limit exceeded for IP {} on path {}", ip, path);
                httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                httpResponse.getWriter().write("Too many requests. Try again later.");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
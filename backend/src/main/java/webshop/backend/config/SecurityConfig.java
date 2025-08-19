package webshop.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import webshop.backend.auth.JwtService;


@Configuration
public class SecurityConfig {


    private final JwtService jwtService;


    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);


        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/token").authenticated() // Basic Auth here
                .anyRequest().authenticated() // JWT for others
        );


        http.httpBasic(basic -> {}); // enable Basic Auth


        http.addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
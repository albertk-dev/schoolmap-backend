package com.albertk.schoolmap.configuration;

import com.albertk.schoolmap.security.JwtFilter;
import com.albertk.schoolmap.security.JwtUtil;
import com.albertk.schoolmap.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter(UserService userService, JwtUtil jwtUtil) {
        return new JwtFilter(jwtUtil, userService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register",
                                "/swagger-ui/**", "/v3/api-docs/**" ).permitAll() // Tout le monde peut s'inscrire/se connecter

                        // !!!ici si j'écrit ROLE_ADMIN se sera interpréter comme ROLE_ROLE_ADMIN
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/schools").hasAnyRole("ADMIN", "DATA_COLLECTOR")
                        .requestMatchers(HttpMethod.POST, "/schools/**").hasAnyRole("ADMIN", "DATA_COLLECTOR")
                        .requestMatchers(HttpMethod.PUT, "/schools/**").hasAnyRole("ADMIN", "DATA_COLLECTOR")
                        .requestMatchers(HttpMethod.DELETE, "/schools/**").hasRole("ADMIN")

                        .anyRequest().authenticated() // Toutes les autres routes nécessitent une authentification
                ).exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Vous n'avez pas les droits pour effectuer cette action.\"}");
                        })
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

package com.example.todo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // отключаем CSRF (для REST + Postman)
                .csrf(csrf -> csrf.disable())

                // разрешаем H2 Console
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // разрешаем все запросы (JWT проверяется в фильтре)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/h2-console/**"
                        ).permitAll()
                        .anyRequest().permitAll()
                )

                // отключаем стандартную форму логина
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form.disable());

        return http.build();
    }
}
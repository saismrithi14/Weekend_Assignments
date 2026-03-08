package com.order_management.Order_management_system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@EnableWebSecurity
@Configuration

public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil)
    {
        this.jwtUtil = jwtUtil;
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users()
    {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails trader = User.builder()
                .username("trader")
                .password(passwordEncoder().encode("trader123")).
                roles("TRADER")
                .build();

        UserDetails viewer = User.builder()
                .username("viewer")
                .password(passwordEncoder().encode("viewer123"))
                .roles("VIEWER")
                .build();
        return new InMemoryUserDetailsManager(admin,trader,viewer);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf().disable();
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/oms/reports/**").hasAnyRole("ADMIN", "TRADER", "VIEWER")
                .requestMatchers(HttpMethod.POST, "/oms/orders/create").hasAnyRole("TRADER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/oms/orders/all").hasAnyRole("ADMIN", "TRADER")
                .requestMatchers(HttpMethod.DELETE, "/oms/orders/*").hasAnyRole("ADMIN")
                .requestMatchers("/oms/member/**").hasRole("ADMIN").anyRequest().authenticated())
                .httpBasic();
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public AuthenticationManager createAuthenticationManager(AuthenticationConfiguration authConfig) throws Exception
    {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, users());
    }
}

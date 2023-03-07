package com.api.hotelreviewapplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.api.hotelreviewapplication.security.ApplicationUserPermission.*;
import static com.api.hotelreviewapplication.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/","index").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/hotels").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/hotel/create").hasAuthority(HOTEL_CREATE.getPermission())
                .requestMatchers(HttpMethod.PUT, "/api/hotel/update/**").hasAuthority(HOTEL_UPDATE.getPermission())
                .requestMatchers(HttpMethod.DELETE, "/api/hotel/delete/*").hasAuthority(HOTEL_DELETE.getPermission())
                .requestMatchers(HttpMethod.GET,"/api/hotel/*/reviews").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/hotel/*/review").hasAuthority(REVIEW_CREATE.getPermission())
                .requestMatchers(HttpMethod.PUT, "/api/hotel/*/review/*").hasAuthority(REVIEW_UPDATE.getPermission())
                .requestMatchers(HttpMethod.DELETE, "/api/hotel/*/review/*").hasAuthority(REVIEW_DELETE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .authorities(ADMIN.grantedAuthorities())
                .build();
        UserDetails moderator = User.builder()
                .username("moderator")
                .password(passwordEncoder.encode("password"))
                .authorities(MODERATOR.grantedAuthorities())
                .build();
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .authorities(USER.grantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(admin, moderator, user);
    }
}

package com.api.hotelreviewapplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.api.hotelreviewapplication.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

package com.api.hotelreviewapplication.authentication;

import com.api.hotelreviewapplication.Token.Token;
import com.api.hotelreviewapplication.Token.TokenRepository;
import com.api.hotelreviewapplication.model.Role;
import com.api.hotelreviewapplication.model.User;
import com.api.hotelreviewapplication.repository.RoleRepository;
import com.api.hotelreviewapplication.repository.UserRepository;
import com.api.hotelreviewapplication.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository,
                                 UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

        public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthenticationResponse();
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());

        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRole(userRole);
            var savedUser  = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
//            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
//                    .refreshToken(refreshToken)
                    .build();
    }
    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(
                request.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateToken(user);
//        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
                .build();
    }
    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }

//    private void revokeAllUserTokens(User user) {
//        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}

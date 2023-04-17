package com.api.hotelreviewapplication.authentication;

import com.api.hotelreviewapplication.exception.JwtAuthenticationException;
import com.api.hotelreviewapplication.model.Role;
import com.api.hotelreviewapplication.model.User;
import com.api.hotelreviewapplication.repository.RoleRepository;
import com.api.hotelreviewapplication.repository.UserRepository;
import com.api.hotelreviewapplication.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username is taken!";
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());

        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRole(userRole);
        userRepository.save(user);
        return "User registered success! Now please login";
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtService.generateToken(authentication);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } catch (UsernameNotFoundException ex) {
            throw new JwtAuthenticationException("User not found");
        } catch (BadCredentialsException ex) {
            throw new JwtAuthenticationException("Invalid username/password supplied");
        }
    }
}
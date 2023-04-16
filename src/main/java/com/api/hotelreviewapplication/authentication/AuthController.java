package com.api.hotelreviewapplication.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("")
public class AuthController {
    private AuthenticationService service;

    @Autowired
    public AuthController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        service.refreshToken(request, response);
//    }

//    private AuthenticationManager authenticationManager;
//    private CustomUserDetailsService userDetailsService;
//    private UserRepository userRepository;
//    private RoleRepository roleRepository;
//    private PasswordEncoder passwordEncoder;
//    private JwtService jwtService;
//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager,
//                          CustomUserDetailsService userDetailsService,
//                          UserRepository userRepository,
//                          RoleRepository roleRepository,
//                          PasswordEncoder passwordEncoder,
//                          JwtService jwtService) {
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtService = jwtService;
//    }
//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody RegisterRequest registerDto) {
//        if (userRepository.existsByUsername(registerDto.getUsername())) {
//            return new ResponseEntity<>("User is taken!", HttpStatus.BAD_REQUEST);
//        }
//        User user = new User();
//        user.setFirstname(registerDto.getFirstname());
//        user.setLastname(registerDto.getLastname());
//        user.setUsername(registerDto.getUsername());
//        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//
//        Role userRole = roleRepository.findByName("ROLE_USER").get();
//        user.setRole(userRole);
//
//        userRepository.save(user);
//        CustomUserDetails userDetails = new CustomUserDetails(user);
//
//        return new ResponseEntity<>("User registered success!",HttpStatus.OK);
//    }
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequest loginDto) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken
//                        (loginDto.getUsername(), loginDto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwtToken = jwtService.generateToken(authentication);
//        return new ResponseEntity<>(new AuthResponseDTO(jwtToken), HttpStatus.OK);
//    }
}
package com.baina.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baina.auth.entity.AppUser;
import com.baina.auth.repository.AppUserRepository;
import com.baina.auth.dto.AuthRequest;
import com.baina.auth.dto.AuthResponse;
import com.baina.auth.dto.RegisterRequest;
import com.baina.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).build();
        }
        Map<String, Object> claims = new HashMap<>();
        AppUser user = userRepository.findByUsername(req.getUsername()).get();
        claims.put("roles", user.getRoles());
        String token = jwtUtil.generateToken(req.getUsername(), claims);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.status(409).build();
        }
        AppUser user = new AppUser();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRoles(Set.of(req.getRole() != null ? req.getRole() : "USER"));
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername(), Map.of("roles", user.getRoles()));
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

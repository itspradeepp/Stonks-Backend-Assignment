package com.stonks.controller;

import com.stonks.model.Profile;
import com.stonks.model.LoginRequest;
import com.stonks.repository.ProfileRepository;
import com.stonks.security.JwtUtil;
import com.stonks.service.TwoFactorAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Profile profile) {
        if (profileRepository.existsByEmail(profile.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken!");
        }
        if (profileRepository.existsByUsername(profile.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        profile.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        profile.setActive(true);
        profileRepository.save(profile);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/2fa/enable")
    public ResponseEntity<?> enable2FA(@RequestParam String email) {
        Optional<Profile> profileOpt = profileRepository.findByEmail(email);
        if (profileOpt.isPresent()) {
            Profile profile = profileOpt.get();
            String secretKey = twoFactorAuthService.generateSecretKey();
            profile.setTwoFactorAuthSecret(secretKey);
            profileRepository.save(profile);
            return ResponseEntity.ok(secretKey);
        }
        return ResponseEntity.status(404).body("Profile not found");
    }

    @PostMapping("/2fa/validate")
    public ResponseEntity<?> validate2FA(@RequestParam String email, @RequestParam int code) {
        Optional<Profile> profileOpt = profileRepository.findByEmail(email);
        if (profileOpt.isPresent()) {
            Profile profile = profileOpt.get();
            boolean isValid = twoFactorAuthService.validateCode(profile.getTwoFactorAuthSecret(), code);
            if (isValid) {
                return ResponseEntity.ok("2FA validation successful");
            } else {
                return ResponseEntity.status(400).body("Invalid 2FA code");
            }
        }
        return ResponseEntity.status(404).body("Profile not found");
    }
}

class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

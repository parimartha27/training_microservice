package com.crud.microservice.service;

import com.crud.microservice.dto.AuthRequest;
import com.crud.microservice.dto.AuthResponse;
import com.crud.microservice.dto.LoginResponse;
import com.crud.microservice.dto.UserResponse;
import com.crud.microservice.entity.UsersJwtEntity;
import com.crud.microservice.repository.UserRepository;
import com.crud.microservice.utils.JwtTokenUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse registerUser(AuthRequest request) {

        if (userRepository.existsByUsername(request.getUsername()) ) {
            throw new RuntimeException("Username is already taken");
        }

        if (request.getPassword().isEmpty()) {
            throw new RuntimeException("Password can't be null or empty. Please fill the password");
        }

        UsersJwtEntity entity = new UsersJwtEntity();
        entity.setUsername(request.getUsername());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(entity);

        return AuthResponse.builder().username(entity.getUsername()).build();
    }

    public ResponseEntity<Object> loginUser(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        Date expirationDate = jwtTokenUtil.extractExpiration(token);
        long expirationDuration = (expirationDate.getTime() - System.currentTimeMillis()) / 1000;

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .expires(expirationDuration)
                .build();

        return ResponseEntity.ok(response);
    }

    public List<AuthResponse> findAll() {
        List<UsersJwtEntity> users = userRepository.findAll();
        return users.stream().map(user -> AuthResponse.builder().username(user.getUsername()).build()).collect(Collectors.toList());
    }
}

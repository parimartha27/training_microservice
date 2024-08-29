package com.crud.microservice.controller;

import com.crud.microservice.dto.AuthRequest;
import com.crud.microservice.dto.AuthResponse;
import com.crud.microservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody AuthRequest request) {
        try {
            AuthResponse response = userService.registerUser(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody AuthRequest request) throws Exception {
        return userService.loginUser(request);
    }

}

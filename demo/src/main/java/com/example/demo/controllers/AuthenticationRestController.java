package com.example.demo.controllers;

import com.example.demo.dtos.requests.AuthRequest;
import com.example.demo.dtos.responses.AuthResponse;
import com.example.demo.entities.Empleado;
import com.example.demo.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthenticationRestController {

    @Value("${app.jwt.expires}")
    private Long EXPIRE_DURATION; // 1 hora

    @Value("${app.jwt.type}")
    private String TOKEN_TYPE;

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            Empleado empleado = (Empleado) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(empleado);
            AuthResponse response = new AuthResponse(accessToken, TOKEN_TYPE, EXPIRE_DURATION);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

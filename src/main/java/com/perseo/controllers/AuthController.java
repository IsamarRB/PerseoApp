package com.perseo.controllers;

import com.perseo.dtos.request.LoginRequest;
import com.perseo.dtos.request.RegisterRequest;
import com.perseo.dtos.response.AuthResponse;
import com.perseo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {return ResponseEntity.ok(authService.login(request));}

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {return ResponseEntity.ok(authService.register(request));}
}

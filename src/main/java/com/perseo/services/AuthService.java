package com.perseo.services;

import com.perseo.dtos.request.LoginRequest;
import com.perseo.dtos.request.RegisterRequest;
import com.perseo.dtos.response.AuthResponse;
import com.perseo.models.User;
import com.perseo.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest login) {
        // Realizar autenticación
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );
        } catch (Exception e) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        UserDetails user = (UserDetails) iUserRepository.findByUsername(login.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar el token JWT
        String token = jwtService.getTokenService(user);

        return AuthResponse
                .builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest register) {
        if (iUserRepository.findByUsername(register.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }


        User user = new User();
        user.setUsername(register.getUsername());
        user.setEmail(register.getEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(register.getRole());

        iUserRepository.save(user);

        // Generar el token JWT
        String token = jwtService.getTokenService(user);

        return AuthResponse
                .builder()
                .token(token)
                .role(register.getRole())
                .build();
    }
}

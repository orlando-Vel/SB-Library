package com.example.library.controller;

import com.example.library.model.User;
import com.example.library.service.AuthService;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        // 1. Verificar si el usuario ya existe
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está registrado");
        }

        // 2. Codificar la contraseña antes de guardarla
        user.setPassword(authService.encodePassword(user.getPassword()));

        // 3. Guardar el usuario en la base de datos
        User savedUser = userService.createUser(user);

        // 4. Generar el token JWT
        String token = authService.generateToken(savedUser);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody User user) {
        // 1. Autenticar al usuario usando Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        // 2. Establecer la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Obtener el usuario autenticado
        User authenticatedUser = userService.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 4. Generar el token JWT
        String token = authService.generateToken(authenticatedUser);

        return ResponseEntity.ok(token);
    }
}   
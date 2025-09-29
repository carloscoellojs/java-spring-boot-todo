package com.ccjava.todos_app.controller;

import com.ccjava.todos_app.model.*;
import com.ccjava.todos_app.service.UserService;
import com.ccjava.todos_app.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;

    @Operation(summary = "Register a new user", description = "Creates a new user with the provided username, email, and password.",
    responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = ApiAuthRegisterMessage.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Username is already taken or validation error",
                    content = @Content(schema = @Schema(implementation = ApiAuthRegisterMessage.class))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) throws Exception {
        if(userService.existsByUsername(registerDto.getUsername())) {
            ApiAuthRegisterMessage apiAuthRegisterMessage = ApiAuthRegisterMessage.builder()
                    .message("Username is already taken")
                    .build();
            return new ResponseEntity<>(apiAuthRegisterMessage, HttpStatus.BAD_REQUEST);
        }

        User user1 = User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role("ROLE_USER")
                .build();

        userService.save(user1);
        ApiAuthRegisterMessage apiAuthRegisterMessage = ApiAuthRegisterMessage.builder()
                .message("User registered successfully")
                .build();
        return new ResponseEntity<>(apiAuthRegisterMessage, HttpStatus.CREATED);
    }

    @Operation(summary = "Authenticate user when logging in", description = "Authenticates a user when logging in provided username, and password. Returns a JWT token if successful.", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully",
                    content =  @Content(schema = @Schema(implementation = ApiAuthAuthenticateMessage.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid username or password",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticateDto authenticateDto) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticateDto.getUsername(), authenticateDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ApiAuthAuthenticateMessage apiAuthAuthenticateMessageMessage = ApiAuthAuthenticateMessage.builder()
                .message("User authenticated successfully")
                .token(jwtUtil.generateToken(authenticateDto.getUsername()))
                .build();
        return new ResponseEntity<>(apiAuthAuthenticateMessageMessage, HttpStatus.OK);
    }
}

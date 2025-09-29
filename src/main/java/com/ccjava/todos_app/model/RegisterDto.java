package com.ccjava.todos_app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {

    @Schema(description = "Username must be between 5 and 15 characters", example = "bob_smith")
    @NotBlank(message = "Must have characters not just blank spaces")
    @NotEmpty(message = "Username is required")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    private String username;

    @NotBlank(message = "Must have characters not just blank spaces")
    @NotEmpty(message = "Email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Email should be valid, email format example: smith@example.com")
    private String email;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$", message = "Must be at least characters 6 long, one UpperCase character and one number")
    private String password;
}

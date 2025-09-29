package com.ccjava.todos_app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateDto {

    @Schema(description = "Username must be between 5 and 15 characters", example = "bob_smith")
    @NotBlank(message = "Must have characters not just blank spaces")
    @NotEmpty(message = "Username is required")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    private String username;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$", message = "Must be at least characters 6 long, one UpperCase character and one number")
    private String password;
}

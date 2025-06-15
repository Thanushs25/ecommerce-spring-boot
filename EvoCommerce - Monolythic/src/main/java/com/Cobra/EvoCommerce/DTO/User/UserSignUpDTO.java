package com.Cobra.EvoCommerce.DTO.User;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDTO {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Email Id cannot be empty")
    @Email(message = "Enter a valid email id")
    private String email;
    @NotEmpty(message = "User name cannot be empty")
    private String userName;
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = ".{8,}", message = "Password should contain minimum of 8 characters")
    private String password;
    @NotEmpty(message = "Confirm password cannot be empty")
    private String confirmPassword;
}

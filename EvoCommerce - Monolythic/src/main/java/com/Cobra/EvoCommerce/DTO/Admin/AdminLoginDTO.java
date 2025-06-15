package com.Cobra.EvoCommerce.DTO.Admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginDTO {
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = ".{8,}", message = "Password should contain minimum of 8 characters")
    private String password;
}

package com.Cobra.EvoCommerce.DTO.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @NotEmpty(message = "User Name cannot be empty")
    private String userName;
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = ".{8,}", message = "Password should contain minimum of 8 characters")
    private String password;
}

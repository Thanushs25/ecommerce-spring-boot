package com.Cobra.EvoCommerce.DTO.User;

import com.Cobra.EvoCommerce.Model.User.Address;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDTO {
    private String name;
    private String username;
    private String email;
    @Embedded
    private Address address;
}

package com.Cobra.EvoCommerce.DTO.User;

import com.Cobra.EvoCommerce.Model.User.Address;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateDTO {
    private String name;
    @Embedded
    @Valid
    private Address address;
}

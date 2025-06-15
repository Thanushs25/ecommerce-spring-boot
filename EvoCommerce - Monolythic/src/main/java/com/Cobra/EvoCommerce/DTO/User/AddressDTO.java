package com.Cobra.EvoCommerce.DTO.User;

import com.Cobra.EvoCommerce.Model.User.Address;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @Valid
    private Address address;
}

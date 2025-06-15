package com.Cobra.EvoCommerce.Model.Order;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address {
    @NotBlank(message = "Flat Number Must be Positive")
    private String flatNumber;

    @NotBlank(message = "Street Cannot be Blank")
    private String street;

    @NotBlank(message = "City Cannot be Blank")
    private String city;

    @NotBlank(message = "State Cannot be Blank")
    private String state;

    @NotBlank(message = "Country Cannot be Blank")
    private String country;

    @NotBlank(message = "Pincode Cannot be Blank")
    @Pattern(regexp = "\\d{6}", message = "Pincode must be 6 digits")
    private String pincode;
}



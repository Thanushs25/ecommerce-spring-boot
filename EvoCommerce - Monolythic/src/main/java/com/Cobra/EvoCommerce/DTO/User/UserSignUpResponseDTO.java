package com.Cobra.EvoCommerce.DTO.User;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserSignUpResponseDTO {
    private Date createdAt;
    private String name;
    private String userName;
    private String email;
}

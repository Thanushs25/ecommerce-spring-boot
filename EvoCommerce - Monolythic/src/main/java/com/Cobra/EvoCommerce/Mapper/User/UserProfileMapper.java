package com.Cobra.EvoCommerce.Mapper.User;

import com.Cobra.EvoCommerce.DTO.User.UserProfileResponseDTO;
import com.Cobra.EvoCommerce.Model.User.Users;
import org.springframework.stereotype.Service;

@Service
public class UserProfileMapper {

    public UserProfileResponseDTO mapUserToUserProfileResponseDTO(Users user) {
        return UserProfileResponseDTO.builder()
                .name(user.getName())
                .username(user.getUserName())
                .email(user.getEmail())
                .address(user.getAddress())
                .build();
    }
}

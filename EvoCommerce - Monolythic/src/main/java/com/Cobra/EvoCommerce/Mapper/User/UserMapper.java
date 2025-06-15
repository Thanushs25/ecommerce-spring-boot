package com.Cobra.EvoCommerce.Mapper.User;

import com.Cobra.EvoCommerce.DTO.User.AddressDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpDTO;
import com.Cobra.EvoCommerce.DTO.User.UserSignUpResponseDTO;
import com.Cobra.EvoCommerce.Model.User.Users;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public Users mapUserSignUpDTOToUser(UserSignUpDTO userSignUpDTO) {
        return Users.builder()
                .name(userSignUpDTO.getName())
                .email(userSignUpDTO.getEmail())
                .password(userSignUpDTO.getPassword())
                .userName(userSignUpDTO.getUserName())
                .build();
    }

    public UserSignUpResponseDTO mapUserSignUpResponseDTOToUser(Users user) {
        return UserSignUpResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .userName(user.getUserName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public AddressDTO mapUserToAddressDTO(Users user) {
        return AddressDTO.builder()
                .address(user.getAddress())
                .build();
    }
}

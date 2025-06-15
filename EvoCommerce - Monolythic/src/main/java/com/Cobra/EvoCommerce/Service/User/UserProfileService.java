package com.Cobra.EvoCommerce.Service.User;

import com.Cobra.EvoCommerce.DTO.User.UserProfileResponseDTO;
import com.Cobra.EvoCommerce.DTO.User.UserProfileUpdateDTO;

import java.util.Map;

public interface UserProfileService {
    UserProfileResponseDTO getUserProfileByUserId(Long userId);

    Map<String, Object> updateUserProfileByUserId(Long userId, UserProfileUpdateDTO userProfileUpdateDTO);
}

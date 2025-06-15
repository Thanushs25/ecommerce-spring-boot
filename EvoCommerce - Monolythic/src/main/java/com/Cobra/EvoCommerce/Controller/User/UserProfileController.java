package com.Cobra.EvoCommerce.Controller.User;

import com.Cobra.EvoCommerce.DTO.Order.OrderItemDTO;
import com.Cobra.EvoCommerce.DTO.User.UserProfileResponseDTO;
import com.Cobra.EvoCommerce.DTO.User.UserProfileUpdateDTO;
import com.Cobra.EvoCommerce.Service.User.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponseDTO> getUserProfileByUserId(@PathVariable Long userId){
        UserProfileResponseDTO userProfileResponseDTO = userProfileService.getUserProfileByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userProfileResponseDTO);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUserProfileByUserId(@PathVariable Long userId, @Valid @RequestBody UserProfileUpdateDTO userProfileUpdateDTO){
        Map<String, Object> updatedFields = userProfileService.updateUserProfileByUserId(userId, userProfileUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedFields);
    }

}

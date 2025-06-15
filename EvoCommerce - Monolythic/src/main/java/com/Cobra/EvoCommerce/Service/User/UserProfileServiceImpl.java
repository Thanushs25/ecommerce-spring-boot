package com.Cobra.EvoCommerce.Service.User;

import com.Cobra.EvoCommerce.DTO.User.UserProfileResponseDTO;
import com.Cobra.EvoCommerce.DTO.User.UserProfileUpdateDTO;
import com.Cobra.EvoCommerce.Mapper.User.UserProfileMapper;
import com.Cobra.EvoCommerce.Model.User.Address;
import com.Cobra.EvoCommerce.Model.User.Users;
import com.Cobra.EvoCommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponseDTO getUserProfileByUserId(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponseDTO dto = userProfileMapper.mapUserToUserProfileResponseDTO(user);
        return dto;
    }

    @Override
    public Map<String, Object> updateUserProfileByUserId(Long userId, UserProfileUpdateDTO dto) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> updatedFields = new HashMap<>();

        if (dto.getName() != null) {
            user.setName(dto.getName());
            updatedFields.put("name", dto.getName());
        }

        if (dto.getAddress() != null) {
            Address existingAddress = user.getAddress() != null ? user.getAddress() : new Address();
            Address newAddress = dto.getAddress();
            Map<String, Object> updatedAddress = new HashMap<>();

            if (newAddress.getFlatNumber() != null) {
                existingAddress.setFlatNumber(newAddress.getFlatNumber());
                updatedAddress.put("flatNo", newAddress.getFlatNumber());
            }
            if (newAddress.getStreet() != null) {
                existingAddress.setStreet(newAddress.getStreet());
                updatedAddress.put("streetName", newAddress.getStreet());
            }
            if (newAddress.getCity() != null) {
                existingAddress.setCity(newAddress.getCity());
                updatedAddress.put("area", newAddress.getCity());
            }
            if (newAddress.getState() != null) {
                existingAddress.setState(newAddress.getState());
                updatedAddress.put("city", newAddress.getState());
            }
            if(newAddress.getCountry() != null){
                existingAddress.setCountry(newAddress.getCountry());
                updatedAddress.put("city", newAddress.getCity());
            }
            if (newAddress.getPincode() != null) {
                existingAddress.setPincode(newAddress.getPincode());
                updatedAddress.put("pinCode", newAddress.getPincode());
            }

            if (!updatedAddress.isEmpty()) {
                user.setAddress(existingAddress);
                updatedFields.put("address", updatedAddress);
            }
        }

        userRepository.save(user);
        return updatedFields;
    }
}

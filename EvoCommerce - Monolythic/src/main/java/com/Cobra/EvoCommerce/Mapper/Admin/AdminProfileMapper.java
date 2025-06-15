package com.Cobra.EvoCommerce.Mapper.Admin;

import com.Cobra.EvoCommerce.DTO.Admin.AdminProfileResponseDTO;
import com.Cobra.EvoCommerce.Model.Admin.Admin;
import org.springframework.stereotype.Service;

@Service
public class AdminProfileMapper {

    public AdminProfileResponseDTO mapAdminToAdminResponseDTO(Admin admin){
        return AdminProfileResponseDTO.builder()
                .username(admin.getUsername())
                .build();
    }
}

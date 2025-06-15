package com.Cobra.EvoCommerce.Service.Admin;

import com.Cobra.EvoCommerce.DTO.Admin.AdminProfileResponseDTO;
import com.Cobra.EvoCommerce.Exception.AdminNotFoundException;
import com.Cobra.EvoCommerce.Mapper.Admin.AdminProfileMapper;
import com.Cobra.EvoCommerce.Model.Admin.Admin;
import com.Cobra.EvoCommerce.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminProfileServiceImpl implements AdminProfileService{

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminProfileMapper adminProfileMapper;

    @Override
    public AdminProfileResponseDTO getAdminProfileById(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found"));

        return adminProfileMapper.mapAdminToAdminResponseDTO(admin);

    }
}

package com.Cobra.EvoCommerce.Service.Admin;

import com.Cobra.EvoCommerce.DTO.Admin.AdminProfileResponseDTO;

public interface AdminProfileService {

    AdminProfileResponseDTO getAdminProfileById(Long adminId);
}

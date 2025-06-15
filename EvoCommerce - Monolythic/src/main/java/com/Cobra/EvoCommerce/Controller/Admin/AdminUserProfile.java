package com.Cobra.EvoCommerce.Controller.Admin;

import com.Cobra.EvoCommerce.DTO.Admin.AdminProfileResponseDTO;
import com.Cobra.EvoCommerce.Service.Admin.AdminProfileService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/profile")
public class AdminUserProfile {

    @Autowired
    private AdminProfileService adminProfileService;

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminProfileResponseDTO> getAdminByAdminId(@PathVariable Long adminId){
        AdminProfileResponseDTO adminProfileResponseDTO = adminProfileService.getAdminProfileById(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(adminProfileResponseDTO);
    }

}

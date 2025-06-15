package com.Cobra.EvoCommerce.Controller.Admin;

import com.Cobra.EvoCommerce.DTO.Admin.AdminLoginDTO;
import com.Cobra.EvoCommerce.Service.Admin.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AdminLoginDTO adminLoginDTO){
        String token = adminService.checkLogin(adminLoginDTO);
        return ResponseEntity.status(200).body(token);
    }

}

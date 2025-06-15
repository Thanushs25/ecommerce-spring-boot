package com.Cobra.EvoCommerce.Service.Admin;

import com.Cobra.EvoCommerce.DTO.Admin.AdminLoginDTO;
import com.Cobra.EvoCommerce.Exception.AdminNotFoundException;
import com.Cobra.EvoCommerce.Model.Admin.Admin;
import com.Cobra.EvoCommerce.Repository.AdminRepository;
import com.Cobra.EvoCommerce.Security.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImp implements AdminService{

    private final AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AdminRepository adminRepository;

    public AdminServiceImp(@Qualifier("adminAuthenticationProvider") AuthenticationProvider authenticationProvider){
        Objects.requireNonNull(authenticationProvider,
                "adminAuthenticationProvider must not be null");
        this.authenticationManager = new ProviderManager(List.of(authenticationProvider));
    }

    @Override
    public String checkLogin(AdminLoginDTO adminLoginDTO) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        adminLoginDTO.getUsername(), adminLoginDTO.getPassword()
                ));

        Admin admin = adminRepository.findByUsername(adminLoginDTO.getUsername())
                .orElseThrow(() -> new AdminNotFoundException("Admin not found"));

        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(adminLoginDTO.getUsername(), "ADMIN",null, admin.getAdminId());
        }
        else {
            throw new BadCredentialsException("Invalid admin Credentials");
        }
    }

}

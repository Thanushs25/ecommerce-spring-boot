package com.Cobra.EvoCommerce.Service.Admin;

import com.Cobra.EvoCommerce.Exception.AdminNotFoundException;
import com.Cobra.EvoCommerce.Model.Admin.Admin;
import com.Cobra.EvoCommerce.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomAdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with username " + username));
        return new User(admin.getUsername(), admin.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}

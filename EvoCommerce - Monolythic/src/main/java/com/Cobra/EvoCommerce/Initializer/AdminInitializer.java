//package com.Cobra.EvoCommerce.Initializer;
//
//import com.Cobra.EvoCommerce.Model.UserAdmin.Admin;
//import com.Cobra.EvoCommerce.Repository.AdminRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class AdminInitializer {
//
//    private final AdminRepository adminRepository;
//
//    @PostConstruct
//    @Transactional
//    public void initializeAdmins() {
//        if (adminRepository.count() == 0) {
//            Admin superAdmin = Admin.builder().name("Admin1").email("admin1@example.com").password("password123").role("super_admin").permission("all").build();
//            adminRepository.save(superAdmin);
//            Admin productAdmin = Admin.builder().name("Admin2").email("admin2@example.com").password("password456").role("product_manager").permission("create,update,delete_products").build();
//            adminRepository.save(productAdmin);
//        }
//    }
//}

//package com.Cobra.EvoCommerce.Initializer;
//
//import com.Cobra.EvoCommerce.Model.Product.Category;
//import com.Cobra.EvoCommerce.Repository.CategoryRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class CategoryInitializer {
//
//    private final CategoryRepository categoryRepository;
//
//    @PostConstruct
//    @Transactional
//    public void initializeCategories() {
//        if (categoryRepository.count() == 0) {
//            List<String> mainCategories = List.of("Electronics", "Cloths", "Footwear");
//            for (String name : mainCategories) {
//                Category category = Category.builder().name(name).build();
//                categoryRepository.save(category);
//            }
//        }
//    }
//}

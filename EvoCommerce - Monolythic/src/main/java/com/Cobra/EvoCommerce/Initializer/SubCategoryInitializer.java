//package com.Cobra.EvoCommerce.Initializer;
//
//import com.Cobra.EvoCommerce.Model.Product.Category;
//import com.Cobra.EvoCommerce.Model.Product.SubCategory;
//import com.Cobra.EvoCommerce.Repository.CategoryRepository;
//import com.Cobra.EvoCommerce.Repository.SubCategoryRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class SubCategoryInitializer {
//    private final SubCategoryRepository subcategoryRepository;
//    private final CategoryRepository categoryRepository;
//
//    @PostConstruct
//    @Transactional
//    public void initializeSubcategories() {
//        if (subcategoryRepository.count() == 0) {
//            Map<String, List<String>> subcategoriesData = new LinkedHashMap<>();
//            subcategoriesData.put("Electronics", List.of("Laptops", "Mobiles"));
//            subcategoriesData.put("Clothing", List.of("Men", "Women", "Kids"));
//            subcategoriesData.put("Footwear", List.of("Men", "Women", "Kids"));
//
//            for (Map.Entry<String, List<String>> entry : subcategoriesData.entrySet()) {
//                String mainCategoryName = entry.getKey();
//                List<String> subcategoryNames = entry.getValue();
//                Category category = categoryRepository.findByName(mainCategoryName);
//
//                for (String subcategoryName : subcategoryNames) {
//                    SubCategory subcategory = SubCategory.builder().name(subcategoryName).category(category).build();
//                    subcategoryRepository.save(subcategory);
//                }
//            }
//        }
//    }
//}

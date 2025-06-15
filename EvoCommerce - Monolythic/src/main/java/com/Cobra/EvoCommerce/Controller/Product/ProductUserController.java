package com.Cobra.EvoCommerce.Controller.Product;


import com.Cobra.EvoCommerce.DTO.Product.ProductRepDTO;
import com.Cobra.EvoCommerce.Service.Product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/user")
@RequiredArgsConstructor
public class ProductUserController {

    private final ProductService productService;

    @GetMapping("/home")
    public List<ProductRepDTO> viewProducts() {
        return productService.view_products();
    }

    @GetMapping("/products/category/{categoryName}")
    public List<ProductRepDTO> productByCategory(@PathVariable String categoryName){
        return productService.view_products_by_category(categoryName);
    }

    @GetMapping("/products/category/{categoryName}/subCategory/{subCategoryName}")
    public List<ProductRepDTO> productByCategoryAndSubCategory(@PathVariable String categoryName,@PathVariable String subCategoryName){
        return productService.view_products_by_category_subcategory(categoryName,subCategoryName);
    }

    @GetMapping("/products/{productId}")
    public ProductRepDTO productById(@PathVariable Long productId){
        return productService.getProductById(productId);
    }


    @GetMapping("/products/category/{categoryName}/priceRange/{minPrice}/{maxPrice}")
    public List<ProductRepDTO> view_product_by_category_priceRange(@PathVariable String categoryName, @PathVariable Double minPrice, @PathVariable Double maxPrice) {
        return productService.view_product_by_category_priceRange(categoryName, minPrice, maxPrice);
    }


    @GetMapping("/products/category/{categoryName}/subCategory/{subcategoryName}/priceRange/{minPrice}/{maxPrice}")
    public List<ProductRepDTO> view_product_by_category_subcategory_priceRange(@PathVariable String categoryName,@PathVariable String subcategoryName ,@PathVariable Double minPrice, @PathVariable Double maxPrice) {
        return productService.view_product_by_category_subcategory_priceRange(categoryName,subcategoryName,minPrice,maxPrice);
    }
}

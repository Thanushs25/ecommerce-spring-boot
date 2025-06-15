package com.Cobra.EvoCommerce.Repository;

import com.Cobra.EvoCommerce.Model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE Lower(p.subcategory.category.name) = Lower(:categoryName)")
    List<Product> findByCategory(@Param("categoryName") String categoryName);


    @Query("SELECT p FROM Product p WHERE Lower(p.subcategory.category.name) = Lower(:categoryName) AND LOWER(p.subcategory.name) = LOWER(:subCategoryName)")
    List<Product> findByCategoryAndSubCategory(@Param("categoryName") String categoryName, @Param("subCategoryName") String subCategoryName);


    @Query("SELECT DISTINCT p FROM Product p JOIN p.productVariants pv " + "WHERE LOWER(p.subcategory.category.name) = LOWER(:categoryName) " + "AND pv.price BETWEEN :minPrice AND :maxPrice AND pv.stockStatus='available' ")
    List<Product> findByCategoryAndPriceRange(@Param("categoryName") String categoryName, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.productVariants pv " +
            "WHERE LOWER(p.subcategory.category.name) = LOWER(:categoryName) " +
            "AND LOWER(p.subcategory.name) = LOWER(:subcategoryName) " +
            "AND pv.price BETWEEN :minPrice AND :maxPrice AND pv.stockStatus='available'")
    List<Product> findByCategoryAndSubCategoryAndPriceRange(@Param("categoryName") String categoryName, @Param("subcategoryName") String subcategoryName, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    List<Product> findByAdmin_adminId(@Param("adminId") Long adminId);


}

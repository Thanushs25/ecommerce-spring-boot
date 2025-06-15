package com.Cobra.EvoCommerce.Repository;

import com.Cobra.EvoCommerce.Model.Product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String mainCategoryName);
}

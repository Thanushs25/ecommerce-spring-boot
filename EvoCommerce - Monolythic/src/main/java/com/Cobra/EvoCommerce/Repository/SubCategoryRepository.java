package com.Cobra.EvoCommerce.Repository;

import com.Cobra.EvoCommerce.Model.Product.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
}

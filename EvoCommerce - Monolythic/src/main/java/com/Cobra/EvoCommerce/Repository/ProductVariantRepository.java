package com.Cobra.EvoCommerce.Repository;

import com.Cobra.EvoCommerce.Model.Product.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant,Long> {
}

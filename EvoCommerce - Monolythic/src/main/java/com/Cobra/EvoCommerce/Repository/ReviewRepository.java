package com.Cobra.EvoCommerce.Repository;

import com.Cobra.EvoCommerce.Model.Review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByProduct_productId(Long productId);
    List<Review> findByUsers_userId(Long userId);
}

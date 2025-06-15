package com.Cobra.EvoCommerce.Repository;

import com.Cobra.EvoCommerce.Model.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByUser_userId(Long userId);

    void deleteAllByUser_userId(Long userId);
//    Optional<ShoppingCart> findByUserIdAndProductIdAndVariantId(Long userId, Long productId, Long variantId);
}

package com.Cobra.EvoCommerce.Repository;

import com.Cobra.EvoCommerce.Model.Cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepo extends JpaRepository<CartItem,Long> {
}

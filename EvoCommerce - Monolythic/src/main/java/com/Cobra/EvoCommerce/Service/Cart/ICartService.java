package com.Cobra.EvoCommerce.Service.Cart;

import com.Cobra.EvoCommerce.DTO.Cart.CartDto;
import com.Cobra.EvoCommerce.Model.Cart.Cart;
import com.Cobra.EvoCommerce.Model.Cart.CartItem;

import java.util.List;

public interface ICartService {
    void addToCart(Long productId, Long variantId, Long userId, int quantity);

    List<CartDto> displayCartItems(Long userId);

    CartItem updateCartItemQuantity(Long userId, Long cartItemId, int quantity);

    void removeCartItem(Long userId, Long cartItemId);

    void clearShoppingCart(Long userId);
}

package com.Cobra.EvoCommerce.Exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String cartItem, String id, Long cartItemId) {

        super("Cart item not found with cartItemId: " + cartItemId);

    }

    public CartNotFoundException(String cartItem) {

    }

}


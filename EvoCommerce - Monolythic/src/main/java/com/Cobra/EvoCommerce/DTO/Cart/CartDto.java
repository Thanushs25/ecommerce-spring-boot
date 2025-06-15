package com.Cobra.EvoCommerce.DTO.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    private Long cartId;
    private Long id;
    private Long userId;
    private Long productId;
    private Long variantId;
    private String imageUrl;
    private String productName;
    private double indPrice;
    private int quantity;
    private double amount;
}

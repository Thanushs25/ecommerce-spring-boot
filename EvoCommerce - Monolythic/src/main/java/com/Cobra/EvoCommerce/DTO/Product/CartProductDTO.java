package com.Cobra.EvoCommerce.DTO.Product;


import lombok.Data;

@Data
public class CartProductDTO {
    private Long productId;
    private String name;
    private String desc;
    private String imageUrl;
    private ProductVariantRepDTO productVariantRepDTO;
}
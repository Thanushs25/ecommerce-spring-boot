package com.Cobra.EvoCommerce.DTO.Product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRepDTO{
    private Long productId;
    private Long adminId;
    private String name;
    private String desc;
    private String imageUrl;
    private String category;
    private String subCategory;
    private List<ProductVariantRepDTO> productVariants;
}

package com.Cobra.EvoCommerce.DTO.Product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateProductDTO {
    private String name;
    private String desc;
    private String imageUrl;
    private Long categoryId;
    private Long subCategoryId;
    private List<UpdateProductVariantDTO> productVariants;
}

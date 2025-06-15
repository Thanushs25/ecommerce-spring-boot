package com.Cobra.EvoCommerce.DTO.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProductDTO{
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotBlank(message = "Product description cannot be blank")
    private String desc;

    @NotBlank(message = "Image URL cannot be blank")
    private String imageUrl;

    @NotNull(message = "Created Admin ID cannot be null")
    @Min(value = 1, message = "Created Admin ID must be at least 1")
    private Long createdAdminId;

    @NotNull(message = "Category ID cannot be null")
    @Min(value = 1, message = "Category ID must be at least 1")
    private Long categoryId;

    @NotNull(message = "Sub-category ID cannot be null")
    @Min(value = 1, message = "Sub-category ID must be at least 1")
    private Long subCategoryId;

    @NotNull(message = "Product variants list cannot be null")
    @NotEmpty(message = "Product must have at least one variant")
    private List<CreateProductVariantDTO> productVariants;
}

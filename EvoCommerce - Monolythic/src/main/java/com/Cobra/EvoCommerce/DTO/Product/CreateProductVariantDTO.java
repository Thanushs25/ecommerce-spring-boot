package com.Cobra.EvoCommerce.DTO.Product;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class CreateProductVariantDTO {
    private Double price;
    private Integer stock;
    private Map<String, String> attributes;
}

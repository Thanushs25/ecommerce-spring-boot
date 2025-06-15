package com.Cobra.EvoCommerce.Mapper.Product;

import com.Cobra.EvoCommerce.DTO.Product.CreateProductVariantDTO;
import com.Cobra.EvoCommerce.DTO.Product.ProductVariantRepDTO;
import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.Product.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {

    @Mapping(target = "stockStatus", ignore = true)
    ProductVariant mapToProductVariant(CreateProductVariantDTO variantDTO, Product product);

    ProductVariantRepDTO mapToProductRep(ProductVariant productVariant);

}

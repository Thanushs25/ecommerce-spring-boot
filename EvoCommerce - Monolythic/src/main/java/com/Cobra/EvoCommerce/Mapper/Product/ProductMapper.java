package com.Cobra.EvoCommerce.Mapper.Product;

import com.Cobra.EvoCommerce.DTO.Product.CreateProductDTO;
import com.Cobra.EvoCommerce.DTO.Product.ProductRepDTO;
import com.Cobra.EvoCommerce.Model.Product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "desc",source = "desc"),
            @Mapping(target = "productId",ignore = true),
            @Mapping(target = "subcategory",ignore=true),
            @Mapping(target = "admin",ignore=true),
            @Mapping(target = "productVariants",ignore=true),
    })
    Product mapToCreateProduct(CreateProductDTO productDTO);

    @Mappings({
            @Mapping(target = "adminId",source = "admin.adminId"),
            @Mapping(target = "category",ignore=true),
            @Mapping(target = "subCategory",ignore=true),
            @Mapping(target = "productVariants",ignore=true),
    })
    ProductRepDTO mapToRepProduct(Product product);



}

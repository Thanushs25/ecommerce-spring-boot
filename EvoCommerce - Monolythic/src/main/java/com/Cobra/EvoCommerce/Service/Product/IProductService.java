package com.Cobra.EvoCommerce.Service.Product;

import com.Cobra.EvoCommerce.DTO.Product.CreateProductDTO;
import com.Cobra.EvoCommerce.DTO.Product.ProductRepDTO;
import com.Cobra.EvoCommerce.DTO.Product.UpdateProductDTO;

import java.util.List;

public interface IProductService {
    ProductRepDTO add_product(CreateProductDTO productDTO);

    ProductRepDTO update_product(UpdateProductDTO productDTO, Long productId, Long adminId);

    boolean delete_product(Long productId, Long adminId);

    List<ProductRepDTO> view_products();

    List<ProductRepDTO> view_products_by_category(String category);

    List<ProductRepDTO> view_products_by_category_subcategory(String categoryName, String subcategoryName);

    ProductRepDTO getProductById(Long productId);

    List<ProductRepDTO> view_product_by_category_priceRange(String categoryName,Double minPrice, Double maxPrice);

    List<ProductRepDTO> view_product_by_category_subcategory_priceRange(String categoryName, String subcategoryName , Double minPrice, Double maxPrice);


    List<ProductRepDTO> view_all_product(Long adminId);

}

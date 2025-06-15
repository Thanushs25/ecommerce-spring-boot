package com.Cobra.EvoCommerce.Service.Product;

import com.Cobra.EvoCommerce.DTO.Product.CreateProductDTO;
import com.Cobra.EvoCommerce.DTO.Product.ProductRepDTO;
import com.Cobra.EvoCommerce.DTO.Product.UpdateProductDTO;
import com.Cobra.EvoCommerce.Exception.ResourceNotFoundException;
import com.Cobra.EvoCommerce.Facade.ProductMapperFacade;
import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.Product.ProductVariant;
import com.Cobra.EvoCommerce.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapperFacade productMapperFacade;
    private final ProductManager productManager;


    @Override
    public ProductRepDTO add_product(CreateProductDTO productDTO) {
        Product product = productMapperFacade.mapToCreateProductEntity(productDTO);
        Product savedProduct = productRepository.save(product);

        return productMapperFacade.mapToProductRepresentation(savedProduct);
    }

    @Override
    public ProductRepDTO update_product(UpdateProductDTO productDTO, Long productId, Long adminId) {
        Product product = productMapperFacade.mapToUpdateProductEntity(productDTO, productId, adminId);
        Product savedProduct = productRepository.save(product);

        return productMapperFacade.mapToProductRepresentation(savedProduct);
    }

    @Override
    public boolean delete_product(Long productId, Long adminId) {
        return productManager.delete_product(productId, adminId);
    }

    //Admin Update Page
    @Override
    public List<ProductRepDTO> view_all_product(Long adminId) {
        return productRepository.findByAdmin_adminId(adminId).stream().map(productMapperFacade::mapToProductRepresentation).toList();
    }


    //user
    //LandingPage
    @Override
    public List<ProductRepDTO> view_products() {
        List<Product> products = productManager.get_products();
        return products.stream().filter(product -> !product.getProductVariants().isEmpty()).map(productMapperFacade::mapToProductRepresentation).toList();
    }

    @Override
    public List<ProductRepDTO> view_products_by_category(String categoryName) {
        List<Product> products = productRepository.findByCategory(categoryName);

        return products.stream().map(product -> {
            List<ProductVariant> mutableVariants = new ArrayList<>(product.getProductVariants());
            mutableVariants.removeIf(variant -> !"available".equalsIgnoreCase(variant.getStockStatus()));
            product.setProductVariants(mutableVariants);
            return product;
        }).filter(product -> !product.getProductVariants().isEmpty()).map(productMapperFacade::mapToProductRepresentation).toList();
    }

    @Override
    public List<ProductRepDTO> view_products_by_category_subcategory(String categoryName, String subcategoryName) {
        List<Product> products = productRepository.findByCategoryAndSubCategory(categoryName, subcategoryName);

        return products.stream().map(product -> {
            List<ProductVariant> mutableVariants = new ArrayList<>(product.getProductVariants());
            mutableVariants.removeIf(variant -> !"available".equalsIgnoreCase(variant.getStockStatus()));
            product.setProductVariants(mutableVariants);
            return product;
        }).filter(product -> !product.getProductVariants().isEmpty()).map(productMapperFacade::mapToProductRepresentation).toList();

    }

    @Override
    public ProductRepDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        List<ProductVariant> availableVariants = product.getProductVariants().stream().filter(variant -> "available".equalsIgnoreCase(variant.getStockStatus())).toList();

        product.setProductVariants(availableVariants);

        return productMapperFacade.mapToProductRepresentation(product);
    }


    @Override
    public List<ProductRepDTO> view_product_by_category_priceRange(String categoryName, Double minPrice, Double maxPrice) {
        List<Product> products = productRepository.findByCategoryAndPriceRange(categoryName, minPrice, maxPrice);
        // exception
        return products.stream().map(product -> {
            List<ProductVariant> mutableVariants = new ArrayList<>(product.getProductVariants());
            mutableVariants.removeIf(variant -> !"available".equalsIgnoreCase(variant.getStockStatus()));
            product.setProductVariants(mutableVariants);
            return product;
        }).filter(product -> !product.getProductVariants().isEmpty()).map(productMapperFacade::mapToProductRepresentation).toList();
    }

    @Override
    public List<ProductRepDTO> view_product_by_category_subcategory_priceRange(String categoryName, String subcategoryName, Double minPrice, Double maxPrice) {
        List<Product> products = productRepository.findByCategoryAndSubCategoryAndPriceRange(categoryName, subcategoryName, minPrice, maxPrice);

        return products.stream().map(product -> {
            List<ProductVariant> mutableVariants = new ArrayList<>(product.getProductVariants());
            mutableVariants.removeIf(variant -> !"available".equalsIgnoreCase(variant.getStockStatus()));
            product.setProductVariants(mutableVariants);
            return product;
        }).filter(product -> !product.getProductVariants().isEmpty()).map(productMapperFacade::mapToProductRepresentation).toList();
    }
}

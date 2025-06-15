package com.Cobra.EvoCommerce.Facade;

import com.Cobra.EvoCommerce.DTO.Product.*;
import com.Cobra.EvoCommerce.Exception.ResourceNotFoundException;
import com.Cobra.EvoCommerce.Mapper.Product.ProductMapper;
import com.Cobra.EvoCommerce.Mapper.Product.ProductVariantMapper;
import com.Cobra.EvoCommerce.Model.Admin.Admin;
import com.Cobra.EvoCommerce.Model.Product.Category;
import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.Product.ProductVariant;
import com.Cobra.EvoCommerce.Model.Product.SubCategory;
import com.Cobra.EvoCommerce.Repository.AdminRepository;
import com.Cobra.EvoCommerce.Repository.CategoryRepository;
import com.Cobra.EvoCommerce.Repository.ProductRepository;
import com.Cobra.EvoCommerce.Repository.SubCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapperFacade {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final ProductVariantMapper productVariantMapper;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public Product mapToCreateProductEntity(CreateProductDTO dto) {
        Product product = productMapper.mapToCreateProduct(dto);
        product.setAdmin(adminRepository.findById(dto.getCreatedAdminId()).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", dto.getCreatedAdminId())));

        product.setSubcategory(this.getSubcategory(dto.getSubCategoryId(), dto.getCategoryId()));

        List<ProductVariant> productVariants = new ArrayList<>();

        for (CreateProductVariantDTO variantDTO : dto.getProductVariants()) {
            ProductVariant productVariant = productVariantMapper.mapToProductVariant(variantDTO, product);
            productVariant.setStockStatus(variantDTO.getStock() > 0 ? "available" : "out of stock");
            productVariants.add(productVariant);
        }
        product.setProductVariants(productVariants);

        return product;
    }

    @Transactional
    public Product mapToUpdateProductEntity(UpdateProductDTO dto, Long productId, Long adminId) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", adminId));

        if (!existingProduct.getAdmin().getAdminId().equals(adminId)) {
            throw new RuntimeException("This product does not belong to this admin");
        }

        if (dto.getName() != null) {
            existingProduct.setName(dto.getName());
        }
        if (dto.getDesc() != null) {
            existingProduct.setDesc(dto.getDesc());
        }
        if (dto.getImageUrl() != null) {
            existingProduct.setImageUrl(dto.getImageUrl());
        }

        if (dto.getSubCategoryId() != null && dto.getCategoryId() != null) {
            SubCategory subcategory = subCategoryRepository.findById(dto.getSubCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Subcategory", "id", dto.getSubCategoryId()));

            Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", dto.getCategoryId()));

            if (!subcategory.getCategory().getCategoryId().equals(category.getCategoryId())) {
                throw new IllegalArgumentException("The provided subcategory does not belong to the specified category.");
            }
            existingProduct.setSubcategory(subcategory);
        } else if (dto.getSubCategoryId() != null) {
            throw new IllegalArgumentException("Category ID must be provided along with Subcategory ID to update category association.");
        } else if (dto.getCategoryId() != null) {
            throw new IllegalArgumentException("Subcategory ID must be provided along with Category ID to update category association.");
        }

        if (dto.getProductVariants() != null && !dto.getProductVariants().isEmpty()) {
            Iterator<ProductVariant> iterator = existingProduct.getProductVariants().iterator();

            while (iterator.hasNext()) {
                ProductVariant variant = iterator.next();
                boolean shouldRemove = dto.getProductVariants().stream().noneMatch(v -> v.getProductVariantId() != null && v.getProductVariantId().equals(variant.getProductVariantId()));

                if (shouldRemove) {
                    iterator.remove(); // Remove variants not referenced in the update DTO
                }
            }

            for (UpdateProductVariantDTO variantDTO : dto.getProductVariants()) {
                if (variantDTO.getProductVariantId() != null) {
                    ProductVariant existingVariant = existingProduct.getProductVariants().stream().filter(variant -> variant.getProductVariantId().equals(variantDTO.getProductVariantId())).findFirst().orElseGet(() -> {
                        ProductVariant newVariant = new ProductVariant();
                        newVariant.setProduct(existingProduct);
                        existingProduct.getProductVariants().add(newVariant);
                        return newVariant;
                    });

                    if (variantDTO.getPrice() != null) {
                        existingVariant.setPrice(variantDTO.getPrice());
                    }

                    if (variantDTO.getStock() != null) {
                        existingVariant.setStock(variantDTO.getStock());
                        existingVariant.setStockStatus(existingVariant.getStock() > 0 ? "available" : "out of stock");
                    }

                    if (variantDTO.getAttributes() != null) {
                        existingVariant.setAttributes(variantDTO.getAttributes());
                    }
                }else{
                    throw new RuntimeException("variant id is important");
                }
            }
        }
        return existingProduct;
    }

    private SubCategory getSubcategory(Long subcategoryId, Long categoryId) throws ResourceNotFoundException {
        if (subcategoryId == null && categoryId == null) {
            return null;
        }
        if (subcategoryId != null) {
            SubCategory subcategory = subCategoryRepository.findById(subcategoryId).orElseThrow(() -> new ResourceNotFoundException("Subcategory", "id", subcategoryId));
            if (categoryId != null) {
                Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
                if (subcategory != null && category != null && !subcategory.getCategory().getCategoryId().equals(category.getCategoryId())) {
                    throw new IllegalArgumentException("The provided subcategory is not relevant to the specified category.");
                }
            }
            return subcategory;

        } else {
            throw new IllegalArgumentException("Subcategory ID must be provided along with Category ID.");
        }
    }

    public ProductRepDTO mapToProductRepresentation(Product product) {
        ProductRepDTO productRepDTO = productMapper.mapToRepProduct(product);
        productRepDTO.setCategory(product.getSubcategory().getCategory().getName());
        productRepDTO.setSubCategory(product.getSubcategory().getName());
        List<ProductVariantRepDTO> productVariantRepDTOS = new ArrayList<>();
        for (ProductVariant productVariant : product.getProductVariants()) {
            productVariantRepDTOS.add(productVariantMapper.mapToProductRep(productVariant));
        }
        productRepDTO.setProductVariants(productVariantRepDTOS);
        return productRepDTO;
    }
}

package com.Cobra.EvoCommerce.Service.Product;

import com.Cobra.EvoCommerce.Exception.ResourceNotFoundException;
import com.Cobra.EvoCommerce.Model.Admin.Admin;
import com.Cobra.EvoCommerce.Model.Order.OrderItem;
import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.Review.Review;
import com.Cobra.EvoCommerce.Repository.AdminRepository;
import com.Cobra.EvoCommerce.Repository.OrderItemsRepo;
import com.Cobra.EvoCommerce.Repository.ProductRepository;
import com.Cobra.EvoCommerce.Repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductManager {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;
    private final OrderItemsRepo itemsRepo;
    private final ReviewRepository reviewRepository;

    @Transactional
    public boolean delete_product(Long productId, Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", "id", adminId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        List<OrderItem> items = itemsRepo.findByProduct_productId(productId);
        itemsRepo.deleteAll(items);

        List<Review> reviews = reviewRepository.findByProduct_productId(productId);
        reviewRepository.deleteAll(reviews);


        if (product.getAdmin().getAdminId().equals(adminId)) {
            productRepository.deleteById(productId);
            return true;
        } else {
            throw new RuntimeException("Product Not Belong to this Admin");
        }
    }

    public List<Product> get_products() {
        // Pageable pageable = PageRequest.ofSize(20);
        List<Product> page = productRepository.findAll();
        return page.stream().map(product -> {
            product.getProductVariants().removeIf(variant -> !"available".equalsIgnoreCase(variant.getStockStatus()));
            return product;
        }).collect(Collectors.toList());
    }
}

package com.Cobra.EvoCommerce.Model.Order;

import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.Product.ProductVariant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "order_Id")
        private Order order;

        @ManyToOne
        @JoinColumn(name = "product_id", nullable = true)
        private Product product;

        @ManyToOne
        @JoinColumn(name = "variant_id", nullable = true)
        private ProductVariant productVariant;

        private int quantity;

        private double priceAtAddition;

        private String imageUrl;

}

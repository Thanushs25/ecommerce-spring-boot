package com.Cobra.EvoCommerce.Model.Cart;


import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.Product.ProductVariant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_CartItem_Product",
            foreignKeyDefinition = "FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE"))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "variant_id", // Nullable typically not needed for cart items
            foreignKey = @ForeignKey(name = "FK_CartItem_ProductVariant",
                    foreignKeyDefinition = "FOREIGN KEY (variant_id) REFERENCES product_variant(product_variant_id) ON DELETE CASCADE"))
    private ProductVariant productVariant;

    private int quantity;

    private double indPrice;
    
    private double priceAtAddition;

    private String imageUrl;
}

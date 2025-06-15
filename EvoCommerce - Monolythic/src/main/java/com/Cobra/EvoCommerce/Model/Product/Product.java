package com.Cobra.EvoCommerce.Model.Product;

import com.Cobra.EvoCommerce.Model.Admin.Admin;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String desc;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "SubcategoryId", nullable = false)
    private SubCategory subcategory;

    @ManyToOne
    @JoinColumn(name = "Admin")
    private Admin admin;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> productVariants;
}

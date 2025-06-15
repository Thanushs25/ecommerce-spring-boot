package com.Cobra.EvoCommerce.Model.Product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productVariantId;


    private double price;

    private int stock;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "Attributes", columnDefinition = "JSON")
    private Map<String, String> attributes;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    private String stockStatus;
}

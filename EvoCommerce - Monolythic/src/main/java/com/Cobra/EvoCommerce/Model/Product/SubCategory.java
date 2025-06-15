package com.Cobra.EvoCommerce.Model.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subcategoryId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "subcategory")
    private List<Product> products;
}

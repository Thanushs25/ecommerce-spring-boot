package com.Cobra.EvoCommerce.Model.Review;


import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.User.Users;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String review;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;


}

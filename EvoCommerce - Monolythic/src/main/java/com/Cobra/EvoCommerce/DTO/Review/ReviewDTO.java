package com.Cobra.EvoCommerce.DTO.Review;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;

    private String userName;

    private Integer rating;

    private Long userId;

    private Long productId;

    private String review;
}

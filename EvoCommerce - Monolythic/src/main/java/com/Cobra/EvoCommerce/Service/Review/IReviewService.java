package com.Cobra.EvoCommerce.Service.Review;

import com.Cobra.EvoCommerce.DTO.Review.ReviewDTO;

import java.util.List;

public interface IReviewService {
    ReviewDTO addReview(ReviewDTO reviewDTO);
    List<ReviewDTO> getReviewByProductId(Long productId);
    List<ReviewDTO> getReviewByUserId(Long userId);
    boolean deleteReviewById(Long userId,Long reviewId);

    ReviewDTO updateReviewById(Long userId,Long reviewId,ReviewDTO reviewDTO) throws IllegalAccessException;
}

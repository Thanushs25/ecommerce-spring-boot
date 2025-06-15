package com.Cobra.EvoCommerce.Controller.Review;


import com.Cobra.EvoCommerce.DTO.Review.ReviewDTO;
import com.Cobra.EvoCommerce.Service.Review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/user/review/add")
    public ReviewDTO addReview(@RequestBody ReviewDTO reviewDTO){
        return reviewService.addReview(reviewDTO);
    }
//
//    @GetMapping("/getReviewByUserId/{userId}")
//    public List<ReviewDTO> view_review_userId(@PathVariable Long userId){
//        return reviewService.getReviewByUserId(userId);
//    }

    @GetMapping("/review/getReviewByProductId/{productId}")
    public List<ReviewDTO> view_review_productId(@PathVariable Long productId){
        return reviewService.getReviewByProductId(productId);
    }

    @PatchMapping("/user/review/{userId}/updateReviewById/{reviewId}")
    public ReviewDTO update_review(@PathVariable Long userId,@PathVariable Long reviewId,@RequestBody ReviewDTO reviewDTO) {
        try {
            return reviewService.updateReviewById(userId,reviewId,reviewDTO);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/user/review/{userId}/deleteReviewById/{reviewId}")
    public boolean delete_review_by_id(@PathVariable Long userId,@PathVariable Long reviewId){
        return reviewService.deleteReviewById(userId,reviewId);
    }

}

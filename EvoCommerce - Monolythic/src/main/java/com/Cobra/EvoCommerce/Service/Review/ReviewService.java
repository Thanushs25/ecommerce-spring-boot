package com.Cobra.EvoCommerce.Service.Review;

import com.Cobra.EvoCommerce.DTO.Review.ReviewDTO;
import com.Cobra.EvoCommerce.Model.Order.OrderItem;
import com.Cobra.EvoCommerce.Exception.ResourceNotFoundException;
import com.Cobra.EvoCommerce.Exception.ReviewException;
import com.Cobra.EvoCommerce.Model.Order.Order;
import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.Review.Review;
import com.Cobra.EvoCommerce.Model.User.Users;
import com.Cobra.EvoCommerce.Repository.OrderRepo;
import com.Cobra.EvoCommerce.Repository.ProductRepository;
import com.Cobra.EvoCommerce.Repository.ReviewRepository;
import com.Cobra.EvoCommerce.Repository.OrderItemsRepo;
import com.Cobra.EvoCommerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService implements IReviewService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepo orderRepo;
    private final OrderItemsRepo orderItemsRepo;

    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) {

        Long productId = reviewDTO.getProductId();
        Long userId = reviewDTO.getUserId();
        System.out.println(productId + " " + userId);

        List<OrderItem> orderItems = Optional.ofNullable(orderItemsRepo.findByProduct_productId(productId))
                .orElseThrow(() -> new ReviewException("No Order Items found for this product!"));
        System.out.println("Order Items: " + orderItems);

        boolean hasBoughtProduct = false;
        for (OrderItem orderItem : orderItems) {
            Order order = orderRepo.findById(orderItem.getOrder().getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderItem.getOrder().getOrderId()));
            if (order.getUser().getUserId().equals(userId) && order.getOrderStatus().equals("delivered")) {
                hasBoughtProduct = true;
                break;
            }
        }

        if (!hasBoughtProduct) {
            throw new ReviewException(
                    "Sorry! You are not allowed to review this product since you haven't bought it on Urban Cart");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Review review = Review.builder().product(product).users(user).review(reviewDTO.getReview())
                .rating(reviewDTO.getRating()).build();

        Review savedReview = reviewRepository.save(review);

        return ReviewDTO.builder().productId(productId).userId(userId).review(savedReview.getReview())
                .rating(savedReview.getRating()).build();
    }

    @Override
    public List<ReviewDTO> getReviewByProductId(Long productId) {
        List<Review> reviews = Optional.ofNullable(reviewRepository.findByProduct_productId(productId))
                .orElseThrow(() -> new ResourceNotFoundException("Review", "Product_Id", productId));

        List<ReviewDTO> reviewDTOS = new ArrayList<>();

        for (Review review : reviews) {
            reviewDTOS.add(ReviewDTO.builder().reviewId(review.getReviewId()).userName(review.getUsers().getName())
                    .productId(productId).userId(review.getUsers().getUserId()).review(review.getReview())
                    .rating(review.getRating()).build());
        }

        return reviewDTOS;
    }

    @Override
    public List<ReviewDTO> getReviewByUserId(Long userId) {
        List<Review> reviews = Optional.ofNullable(reviewRepository.findByUsers_userId(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User", "User_Id", userId));

        List<ReviewDTO> reviewDTOS = new ArrayList<>();

        for (Review review : reviews) {
            reviewDTOS.add(ReviewDTO.builder().productId(review.getProduct().getProductId()).userId(userId)
                    .review(review.getReview()).rating(review.getRating()).build());
        }

        return reviewDTOS;
    }

    @Override
    public boolean deleteReviewById(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review", "id", reviewId));

        if (review.getUsers().getUserId().equals(userId)) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }

    @Override
    public ReviewDTO updateReviewById(Long userId, Long reviewId, ReviewDTO reviewDTO) throws IllegalAccessException {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review", "id", reviewId));

        if (review.getUsers().getUserId().equals(userId)) {
            review.setReview(reviewDTO.getReview());
            Review savedReview = reviewRepository.save(review);
            return ReviewDTO.builder().productId(review.getProduct().getProductId()).userId(userId)
                    .review(savedReview.getReview()).rating(savedReview.getRating()).build();
        } else {
            throw new IllegalAccessException("This review not belong to this user");
        }
    }
}
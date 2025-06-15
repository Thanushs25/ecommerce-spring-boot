package com.Cobra.EvoCommerce.DTO.Order;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long orderItemId;
    private Long productId;
    private Long variantId;
    private Long userId;
    private String productName;
    private String imageUrl;
    private int quantity;
    private String orderStatus;
    private double price;
    private Date date;
    private String stockStatus;

}

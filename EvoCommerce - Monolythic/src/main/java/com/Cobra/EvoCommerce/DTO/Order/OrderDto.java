package com.Cobra.EvoCommerce.DTO.Order;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long userId;
    private Long orderId;
    private String orderStatus;
    private String paymentMethod;
    private String paymentStatus;
    private double finalAmount;
    private Date orderDate;
    private List<OrderItemDTO> orderItems;
}

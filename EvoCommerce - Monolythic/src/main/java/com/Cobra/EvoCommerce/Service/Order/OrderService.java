package com.Cobra.EvoCommerce.Service.Order;

import java.text.ParseException;
import java.util.List;

import com.Cobra.EvoCommerce.DTO.Order.OrderItemDTO;
import com.Cobra.EvoCommerce.Model.Order.OrderItem;

public interface OrderService {


    void createOrders(Long cartId) throws ParseException;


    List<OrderItemDTO> getOrdersByOrderId(Long orderId);


    List<OrderItemDTO> getOrdersByUserId(Long userId);


//    List<Order> getOrdersByOrderStatus(String orderStatus);


//    List<Order> getOrdersByPaymentStatus(String paymentStatus);

    
    String setStatus( Long orderId, String status);

    boolean deleteOrderItemById(Long itemId);
}
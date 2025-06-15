package com.Cobra.EvoCommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Cobra.EvoCommerce.Model.Order.OrderItem;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItem, Long> {

    @Query("SELECT o from OrderItem o WHERE o.order.orderDate BETWEEN :startDate AND :endDate")
    List<OrderItem> findOrderByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    // List<Order> findByPaymentStatus(String paymentStatus);
    // List<Order> findByOrderStatus(String orderStatus);

    List<OrderItem> findByProduct_productId(Long productId);
    // List<Order> findByUserId(Long userId);

    // List<Order> findByUserIdForUser(Long userId);

    // @Modifying
    // @Query("UPDATE Order o SET o.shippingAddress = :shippingAddress WHERE
    // o.orderId = :orderId")
    // void updateShippingAddressByOrderId(@Param("shippingAddress") ShippingAddress
    // shippingAddress, @Param("orderId") Long orderId);
    // @Modifying
    // @Query("UPDATE Order o SET o.address = :address WHERE o.orderId = :orderId")
    // void updateShippingAddressByOrderId(@Param("address") Address address,
    // @Param("orderId") Long orderId);
}
package com.Cobra.EvoCommerce.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cobra.EvoCommerce.Model.Order.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepo extends JpaRepository<Order, Long> {
//    List<Order> findByOrderStatus(String orderStatus);
//    List<Order> findByPaymentStatus(String paymentStatus);
//    List<Order> findByUserId(Long userId);

    List<Order> findByUser_userId(Long userId);
    @Query("SELECT SUM(o.finalAmount) FROM Order o")
    Double getTotalFinalAmount();
    ;
}

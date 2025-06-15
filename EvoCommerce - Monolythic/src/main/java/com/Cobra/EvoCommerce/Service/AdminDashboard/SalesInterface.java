package com.Cobra.EvoCommerce.Service.AdminDashboard;

import com.Cobra.EvoCommerce.DTO.Order.OrderItemDTO;
import com.Cobra.EvoCommerce.DTO.Product.ProductRepDTO;

import java.text.ParseException;
import java.util.List;

public interface SalesInterface {

    List<OrderItemDTO> displayOrders();

//    public ProductRepDTO maxOrderDetail();
//
//    public ProductRepDTO minOrderDetail();
//
//    public List<OrderItemDTO> getOrderByDate(String startDateStr, String endDateStr) throws ParseException;
//
//    public long orderCount();
//
//    public long productCount();

}

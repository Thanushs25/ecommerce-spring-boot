package com.Cobra.EvoCommerce.DTO.AdminDashboard;

import com.Cobra.EvoCommerce.DTO.Order.OrderItemDTO;
import com.Cobra.EvoCommerce.DTO.Product.ProductRepDTO;
import com.Cobra.EvoCommerce.Model.Order.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class DashboardDTO {

    private String adminName;
    private List<OrderItemDTO> orderedItems;
    private ProductRepDTO maxOrderDetail;
    private ProductRepDTO minOrderDetail;
    private long orderCount;
    private long productCount;
    private long orderItemsCount;
    private double totalPrice;
}

package com.Cobra.EvoCommerce.Controller.AdminDashboard;

import com.Cobra.EvoCommerce.DTO.AdminDashboard.DashboardDTO;
import com.Cobra.EvoCommerce.DTO.Order.OrderItemDTO;
import com.Cobra.EvoCommerce.Service.AdminDashboard.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping("/dashboard/{adminId}")
    public DashboardDTO getDashboardData(@PathVariable Long adminId) {
        DashboardDTO response = new DashboardDTO();
        response.setAdminName(salesService.getAdminByAdminId(adminId));
        response.setOrderedItems(salesService.displayOrders());
//        response.setMaxOrderDetail(salesService.maxOrderDetail());
//        response.setMinOrderDetail(salesService.minOrderDetail());
        response.setOrderCount(salesService.orderCount());
        response.setOrderItemsCount(salesService.orderItemsCount());
        response.setProductCount(salesService.productCount());
        response.setTotalPrice(salesService.getRevenue());
        return response;
    }

    @GetMapping("/getByDate")
    public List<OrderItemDTO> getOrderByDate(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return salesService.getOrderByDate(startDate,endDate);
    }
}

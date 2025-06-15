package com.Cobra.EvoCommerce.Controller.Order;

import com.Cobra.EvoCommerce.DTO.Order.OrderDto;
import com.Cobra.EvoCommerce.Service.Order.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderServiceImp orderService;

    @PostMapping("/setStatus/{orderId}")
    public String setStatus(@PathVariable Long orderId, @RequestParam String status) {
        return orderService.setStatus(orderId, status);
    }

    @GetMapping("/getAllOrders")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }
}

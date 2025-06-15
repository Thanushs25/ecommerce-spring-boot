package com.Cobra.EvoCommerce.Controller.Order;

import com.Cobra.EvoCommerce.DTO.Order.OrderItemDTO;
import com.Cobra.EvoCommerce.Service.Order.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class UserOrderController {
    
    @Autowired
    private OrderServiceImp orderService;

    @PostMapping("/user/orders/{cartId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@PathVariable Long cartId) throws ParseException {
        orderService.createOrders(cartId);
    }


    @GetMapping("/admin/orders/getOrdersByOrderId/{orderId}")
    public List<OrderItemDTO> getOrdersByOrderId(@PathVariable Long orderId){
        return orderService.getOrdersByOrderId(orderId);
    }

    @GetMapping("/user/orders/getOrdersByUserId/{userId}")
    public List<OrderItemDTO> getOrdersByUserId(@PathVariable Long userId){
        return orderService.getOrdersByUserId(userId);
    }

    @DeleteMapping("/user/orders/deleteOrderItemById/{itemId}")
    public boolean deleteOrderItemById(@PathVariable Long itemId){
        return orderService.deleteOrderItemById(itemId);
    }

    // @GetMapping("/getOrdersByUserIdForUser/{userId}")
    // public ResponseEntity<List<OrderForUserDTO>> getOrdersByUserIdForUser(@PathVariable Long userId) {
    //     List<OrderForUserDTO> orders = orderService.getOrdersByUserIdForUser(userId);
    //     return new ResponseEntity<>(orders, HttpStatus.OK);
    // }



//    @GetMapping("/getOrdersByOrderStatus/{orderStatus}")
//    public ResponseEntity<List<Order>> getOrdersByOrderStatus(@PathVariable String orderStatus){
//        List<Order> orders =orderService.getOrdersByOrderStatus(orderStatus);
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }




//    @GetMapping("/getOrdersByPaymentStatus/{paymentStatus}")
//    public ResponseEntity<List<Order>> getOrdersByPaymentStatus(@PathVariable String paymentStatus){
//        List<Order> orders = orderService.getOrdersByPaymentStatus(paymentStatus);
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }




}

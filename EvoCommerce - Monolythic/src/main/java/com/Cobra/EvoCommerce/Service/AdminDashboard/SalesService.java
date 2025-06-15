package com.Cobra.EvoCommerce.Service.AdminDashboard;

import com.Cobra.EvoCommerce.DTO.Order.OrderItemDTO;
import com.Cobra.EvoCommerce.DTO.Product.ProductRepDTO;
import com.Cobra.EvoCommerce.Model.Order.Order;
import com.Cobra.EvoCommerce.Model.Order.OrderItem;
import com.Cobra.EvoCommerce.Repository.AdminRepository;
import com.Cobra.EvoCommerce.Repository.OrderItemsRepo;
import com.Cobra.EvoCommerce.Repository.OrderRepo;
import com.Cobra.EvoCommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesService implements SalesInterface {

    @Autowired
    private OrderItemsRepo orderItemsRepo;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private OrderRepo orderRepo;

    public String getAdminByAdminId(Long adminId){
        return adminRepository.findById(adminId).get().getUsername();
    }
    public List<OrderItemDTO> displayOrders() {

        List<OrderItem> orderItems = orderItemsRepo.findAll();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setOrderItemId(orderItem.getId());
            orderItemDTO.setDate(orderItem.getOrder().getOrderDate());
            orderItemDTO.setPrice(orderItem.getPriceAtAddition());
            orderItemDTO.setImageUrl(orderItem.getImageUrl());
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setProductId(orderItem.getProduct().getProductId());
            orderItemDTO.setProductName(orderItem.getProduct().getName());
            orderItemDTO.setVariantId(orderItem.getProductVariant().getProductVariantId());
            orderItemDTO.setUserId(orderItem.getOrder().getUser().getUserId());

            orderItemDTOS.add(orderItemDTO);
        }

        return orderItemDTOS;

    }

//    public ProductRepDTO maxOrderDetail() {
//        List<Order> orders = orderRepo.findAll();
//
//        int max_id = Math.toIntExact(orders.stream()
//                .collect(Collectors.groupingBy(OrderItem::getProductId, Collectors.summingInt(Orders::getQuantity)))
//                .entrySet()
//                .stream()
//                .max(Map.Entry.comparingByValue())
//                .map(Map.Entry::getKey)
//                .orElseThrow(() -> new RuntimeException("No orders found")));
//
//        Product product = productRepository.findById((long) max_id).orElseThrow(() -> new RuntimeException("Product not found"));
//        return productMapper.toDTO(product);
//    }
//
//
//    public ProductDTO minOrderDetail() {
//        List<Orders> orders = ordersRepository.findAll();
//
//        int min_id = Math.toIntExact(orders.stream()
//                .collect(Collectors.groupingBy(Orders::getProduct_id, Collectors.summingInt(Orders::getQuantity)))
//                .entrySet()
//                .stream()
//                .min(Map.Entry.comparingByValue())
//                .map(Map.Entry::getKey)
//                .orElseThrow(() -> new RuntimeException("No orders found")));
//
//        Product product = productRepository.findById((long) min_id).orElseThrow(() -> new RuntimeException("Product not found"));
//        return productMapper.toDTO(product);
//    }
//
    public List<OrderItemDTO> getOrderByDate(String startDateString, String endDateString) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(startDateString);
        Date endDate = formatter.parse(endDateString);

        List<OrderItem> savedOrderItem = orderItemsRepo.findOrderByDate(startDate, endDate);

        List<OrderItemDTO> returnedOrderItem = new ArrayList<>();
        for(OrderItem orderItem:savedOrderItem){
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .productId(orderItem.getProduct().getProductId())
                    .productName(orderItem.getProduct().getName())
                    .date(orderItem.getOrder().getOrderDate())
                    .orderItemId(orderItem.getId())
                    .price(orderItem.getPriceAtAddition())
                    .imageUrl(orderItem.getImageUrl())
                    .quantity(orderItem.getQuantity())
                    .variantId(orderItem.getProductVariant().getProductVariantId())
                    .userId(orderItem.getOrder().getUser().getUserId())
                    .build();
            returnedOrderItem.add(orderItemDTO);
        }
        return returnedOrderItem;
    }

    public long orderCount() {
        try {
            return orderRepo.count();
        } catch (Exception e) {
            return 0L;
        }
    }

    public long orderItemsCount(){
        try {
            return orderItemsRepo.count();
        } catch (Exception e) {
            return 0L;
        }
    }

    public long productCount() {
        try {
            return productRepository.count();
        } catch (Exception e) {
            return 0L;
        }
    }

    public double getRevenue(){
        try {
            return orderRepo.getTotalFinalAmount();
        } catch (Exception e) {
            return 0.0;
        }
    }

}

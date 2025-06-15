package com.Cobra.EvoCommerce.Service.Order;

import com.Cobra.EvoCommerce.DTO.Order.OrderDto;
import com.Cobra.EvoCommerce.DTO.Order.OrderItemDTO;
import com.Cobra.EvoCommerce.Exception.ProductOutOfStockException;
import com.Cobra.EvoCommerce.Exception.ResourceNotFoundException;
import com.Cobra.EvoCommerce.Model.Cart.Cart;
import com.Cobra.EvoCommerce.Model.Cart.CartItem;
import com.Cobra.EvoCommerce.Model.Order.Order;
import com.Cobra.EvoCommerce.Model.Order.OrderItem;
import com.Cobra.EvoCommerce.Model.Product.Product;
import com.Cobra.EvoCommerce.Model.Product.ProductVariant;
import com.Cobra.EvoCommerce.Model.User.Address;
import com.Cobra.EvoCommerce.Model.User.Users;
import com.Cobra.EvoCommerce.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderItemsRepo orderItemsRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createOrders(Long cartId) throws ParseException {
        Cart cart = cartRepo.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException("Cart", "id", cartId));

        Users user = userRepository.findById(cart.getUser().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", cart.getUser().getUserId()));

        Address address = user.getAddress();

        List<CartItem> cartItems = cart.getCartItems();

        LocalDate currentDate = LocalDate.now();

        // Define the desired format for parsing (YY-MM-DD)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        String formattedDateString = currentDate.format(formatter);

        // Create a SimpleDateFormat for parsing the string back into a Date
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        sdf.setLenient(false);

        Order order = Order.builder().orderDate(sdf.parse(formattedDateString)).user(cart.getUser())
                .orderStatus("Confirmed").paymentStatus("Pending").paymentMethod("COD").address(address).build();

        double totalAmount = 0.0;
        List<OrderItem> orderItemsToSave = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            ProductVariant productVariant = productVariantRepository.findById(
                    cartItem.getProductVariant().getProductVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product Variant", "id",
                            cartItem.getProductVariant().getProductVariantId()));

            if (cartItem.getQuantity() > productVariant.getStock()) {
                throw new ProductOutOfStockException("Only " + productVariant.getStock() + " items of variant '" +
                        productVariant.getProductVariantId() + "' are in stock. You requested " + cartItem.getQuantity()
                        + ".");
            }

            // Update stock
            int newStockQuantity = productVariant.getStock() - cartItem.getQuantity();
            productVariant.setStock(newStockQuantity);

            if (newStockQuantity == 0) {
                productVariant.setStockStatus("out of stock");
            } else {
                productVariant.setStockStatus("available"); // Ensure status is correct if stock becomes > 0 again
            }
            productVariantRepository.save(productVariant); // Save updated product variant

            OrderItem item = OrderItem.builder()
                    .product(cartItem.getProduct())
                    .priceAtAddition(cartItem.getPriceAtAddition()) // Price of one item
                    .productVariant(cartItem.getProductVariant())
                    .quantity(cartItem.getQuantity())
                    .order(order)
                    .imageUrl(cartItem.getImageUrl())
                    .build();
            orderItemsToSave.add(item);
            totalAmount += cartItem.getPriceAtAddition();
        }

        totalAmount += (totalAmount * 0.05) + 50;
        order.setFinalAmount(totalAmount);
        orderRepo.save(order);
        orderItemsRepo.saveAll(orderItemsToSave);

        cartRepo.deleteById(cartId);
    }

    @Override
    public List<OrderItemDTO> getOrdersByOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be a positive non-null value.");
        }
        Order order = orderRepo.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order", "id", orderId));
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemDTO orderItemDTO = OrderItemDTO.builder().orderItemId(orderItem.getId())
                    .productId(orderItem.getProduct().getProductId())
                    .variantId(orderItem.getProductVariant().getProductVariantId())
                    .userId(orderItem.getOrder().getUser().getUserId())
                    .productName(orderItem.getProduct().getName())
                    .imageUrl(orderItem.getImageUrl())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPriceAtAddition())
                    .date(orderItem.getOrder().getOrderDate())
                    .build();
            orderItemDTOS.add(orderItemDTO);
        }
        return orderItemDTOS;
    }

    @Override
    public List<OrderItemDTO> getOrdersByUserId(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        List<Order> orders = Optional.ofNullable(orderRepo.findByUser_userId(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Oder", "userId", userId));

        List<OrderItemDTO> orderItems = new ArrayList<>();

        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemDTO orderItemDTO = OrderItemDTO.builder().orderItemId(orderItem.getId())
                        .productId(orderItem.getProduct().getProductId())
                        .variantId(orderItem.getProductVariant().getProductVariantId())
                        .userId(orderItem.getOrder().getUser().getUserId())
                        .productName(orderItem.getProduct().getName())
                        .imageUrl(orderItem.getImageUrl())
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPriceAtAddition())
                        .date(orderItem.getOrder().getOrderDate())
                        .orderStatus(orderItem.getOrder().getOrderStatus())
                        .build();
                orderItems.add(orderItemDTO);
            }
        }
        return orderItems;
    }

    public List<OrderDto> getAllOrders() {
        List<Order> order = orderRepo.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order ord : order) {
            OrderDto orderDto = OrderDto.builder()
                    .userId(ord.getUser().getUserId())
                    .orderId(ord.getOrderId())
                    .orderStatus(ord.getOrderStatus())
                    .paymentMethod(ord.getPaymentMethod())
                    .paymentStatus(ord.getPaymentStatus())
                    .finalAmount(ord.getFinalAmount())
                    .orderDate(ord.getOrderDate())
                    .orderItems(this.getOrderItems(ord)) // Pass a list with a single order
                    .build();
            orderDtos.add(orderDto);
        }
        if (!orderDtos.isEmpty()) {
            return orderDtos;
        }
        return null;
    }

    private List<OrderItemDTO> getOrderItems(Order order) {
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .orderItemId(orderItem.getId())
                    .productId(orderItem.getProduct().getProductId())
                    .variantId(orderItem.getProductVariant().getProductVariantId())
                    .userId(orderItem.getOrder().getUser().getUserId())
                    .productName(orderItem.getProduct().getName())
                    .imageUrl(orderItem.getImageUrl())
                    .quantity(orderItem.getQuantity())
                    .orderStatus(orderItem.getOrder().getOrderStatus())
                    .price(orderItem.getPriceAtAddition())
                    .date(orderItem.getOrder().getOrderDate())
                    .stockStatus(orderItem.getProductVariant().getStockStatus())
                    .variantId(orderItem.getProductVariant().getProductVariantId())
                    .build();
            orderItemDTOS.add(orderItemDTO);
        }
        return orderItemDTOS;
    }

    // @Override
    // public List<Order> getOrdersByOrderStatus(String orderStatus) {
    // if (orderStatus == null || orderStatus.isEmpty()){
    // throw new IllegalArgumentException("Order Status cannot be Null or Empty");
    // }
    //
    // List<Order> orders = orderRepo.findByOrderStatus(orderStatus);
    //
    // if(orders.isEmpty()){
    // throw new IllegalArgumentException("No orders found with the given order
    // status");
    // }
    // return orders;
    // }

    // @Override
    // public List<Order> getOrdersByPaymentStatus(String paymentStatus) {
    // if (paymentStatus == null || paymentStatus.isEmpty()){
    // throw new IllegalArgumentException("Payment status cannot be Empty or Null");
    // }
    //
    // List<Order> orders = orderRepo.findByPaymentStatus(paymentStatus);
    // if(orders.isEmpty()){
    // throw new IllegalArgumentException("No orders found with the given payment
    // status");
    // }
    // return orders;
    // }

    @Override
    public String setStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        if (status.equals("paid")) {
            order.setPaymentStatus(status.toLowerCase());
            order.setOrderStatus("delivered");
        } else if (status.equals("pending")) {
            order.setOrderStatus("Yet to be delivered");
        } else {
            throw new RuntimeException("Status must be paid or pending");
        }

        Order savedOrder = orderRepo.save(order);

        if (savedOrder.getOrderStatus().equals("delivered")) {
            return "delivered";
        }
        return "not delivered";
    }

    @Override
    @Transactional
    public boolean deleteOrderItemById(Long itemId) {
        OrderItem orderItem = orderItemsRepo.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException("Order Item", "Id", itemId));

        Order order = orderRepo.findById(orderItem.getOrder().getOrderId()).orElseThrow(
                () -> new ResourceNotFoundException("Order Item", "Id", itemId));

        if (orderItem.getOrder().getOrderStatus().equals("Confirmed")) {
            ProductVariant productVariant = orderItem.getProductVariant();
            productVariant.setStock(productVariant.getStock() + orderItem.getQuantity());

            order.setFinalAmount(order.getFinalAmount()
                    - (orderItem.getPriceAtAddition() + (orderItem.getPriceAtAddition() * 0.05)));

            if ((productVariant.getStock() + orderItem.getQuantity()) > 0) {
                productVariant.setStockStatus("available");
            } else {
                productVariant.setStockStatus("out of stock");
            }
            orderRepo.save(order);
            productVariantRepository.save(productVariant);
            orderItemsRepo.delete(orderItem);

            if (order.getFinalAmount() <= 50.0) {
                orderRepo.delete(order);
            }
        } else {
            return false;
        }
        return true;
    }

}
package com.Cobra.EvoCommerce.Model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Cobra.EvoCommerce.Model.Cart.Cart;
import com.Cobra.EvoCommerce.Model.User.Address;
import com.Cobra.EvoCommerce.Model.User.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Date orderDate;
//    private Long userId;
    private String orderStatus;
    private String paymentStatus;

    private double finalAmount;

    private String paymentMethod;

    @Embedded
    private Address address;

    @ManyToOne
    private Users user;

    @OneToOne
    @JoinColumn(name = "cart_Id")
    private Cart cart;


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


}
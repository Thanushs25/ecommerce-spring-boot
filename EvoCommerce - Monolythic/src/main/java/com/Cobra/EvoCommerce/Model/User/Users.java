package com.Cobra.EvoCommerce.Model.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private Date createdAt;
    private String name;
    @Column(unique = true, updatable = false)
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;

    @Embedded
    private Address address;

}

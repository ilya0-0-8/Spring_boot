package com.ilya.Misic.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "count")
    private int count;

    @Column(name = "total")
    private int total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

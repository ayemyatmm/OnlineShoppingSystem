package com.example.onlineshoppingsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cart{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="size_value")
    private String size_value;

    @Column(name="total_price")
    private Integer total_price;

    @ManyToOne
    private Product product;
    
    @ManyToOne
    private AppUser appUser;
}

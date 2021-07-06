package com.example.onlineshoppingsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cart{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Please fill to require quantity.")
    @Column(name="quantity")
    private Integer quantity;

    @NotBlank(message = "Please choose to require Product Size.")
    @Column(name="size_value")
    private String size_value;

    @Column(name="total_price")
    private Integer total_price;

    @ManyToOne
    private Product product;
    
    @ManyToOne
    private AppUser appUser;
}

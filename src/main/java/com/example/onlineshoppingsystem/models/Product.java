package com.example.onlineshoppingsystem.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(name="image")
    private String image;

    @NotBlank
    @Size(max = 50)
    @Column(name="name")
    private String name;

    @NotBlank
    @Size(max = 50)
    @Column(name="having_amount")
    private Integer having_amount;

    @NotBlank
    @Size(max = 50)
    @Column(name="price")
    private Integer price;

    @NotBlank
    @Size(max = 255)
    @Column(name="description")
    private String description;

    @NotBlank
    @Size(max = 255)
    @Column(name="customertype")
    private String customertype;

    @OneToMany(mappedBy = "product")
    private List<ProductSize> productSize;

    @OneToMany(mappedBy = "product")
    private List<Cart> cart;
}

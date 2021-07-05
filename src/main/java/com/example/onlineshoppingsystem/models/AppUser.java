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
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please fill to require Username.")
    @Size(max=30)
    @Column(name = "username", nullable = true)
    private String username;

    @NotBlank(message = "Please fill to require Password.")
    @Size(max=255)
    @Column(name = "password", nullable = true)
    private String password;
    
    @Column(name = "enabled")
    private boolean enabled = true;

    @OneToMany(mappedBy = "appUser")
    private List<Cart> cart;

    @OneToMany(mappedBy = "appUser")
    private List<Receipt> receipts;

}

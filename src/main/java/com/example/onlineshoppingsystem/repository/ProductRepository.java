package com.example.onlineshoppingsystem.repository;

import java.util.Collection;

import com.example.onlineshoppingsystem.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository <Product , Long>{
    Collection<Product> findBycustomertype(String cutomertype);
}
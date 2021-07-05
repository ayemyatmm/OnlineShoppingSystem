package com.example.onlineshoppingsystem.repository;

import com.example.onlineshoppingsystem.models.ProductSize;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRepository extends JpaRepository <ProductSize , Integer>{
    
}

package com.example.onlineshoppingsystem.repository;

import java.util.Collection;

import javax.transaction.Transactional;

import com.example.onlineshoppingsystem.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository <Product , Integer>{
    Collection<Product> findBycustomertype(String cutomertype);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update product set having_amount = (having_amount - :cart_quantity) where id = :product_id", nativeQuery = true)
    Integer updateAmount(@Param("cart_quantity") Integer cart_quantity,
    @Param("product_id") Integer product_id);
}
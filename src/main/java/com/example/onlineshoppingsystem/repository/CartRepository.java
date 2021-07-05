package com.example.onlineshoppingsystem.repository;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.example.onlineshoppingsystem.models.AppUser;
import com.example.onlineshoppingsystem.models.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository <Cart, Integer>{
    //Cart findByAppUserId(Integer app_user_id);
    Collection<Cart> findByAppUserId(Integer app_user_id);
    List<Cart> findByAppUser(AppUser appUser);

    @Query(value = "select sum(ct.total_price) from cart as ct where app_user_id = :app_user_id ", nativeQuery = true)
    Integer selectSumPrice(@Param("app_user_id") Integer app_user_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Cart  where cart.app_user_id =:appUser ", nativeQuery = true)
    void deleteCartUser(@Param("appUser") Integer appUser);

    
}

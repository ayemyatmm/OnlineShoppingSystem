package com.example.onlineshoppingsystem.repository;

import java.util.Collection;

import javax.transaction.Transactional;

import com.example.onlineshoppingsystem.models.Receipt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt , Integer>{
    Receipt findByAppUserId(Integer app_user_id);
    @Query(value = "select sum(ct.total_price) from cart as ct where app_user_id = :app_user_id ", nativeQuery = true)
    Integer selectSumPrice(@Param("app_user_id") Integer app_user_id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Receipt  where receipt.app_user_id =:appUser ", nativeQuery = true)
    void deleteContributeur(@Param("appUser") Integer appUser);
}

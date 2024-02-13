package com.ilya.Misic.repositories;

import com.ilya.Misic.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findProductById(@Param("id") Long id);
    @Query("SELECT p FROM Product p WHERE p.user.id= :userId")
    List<Product> findByUserId(@Param("userId") Long userId);
}

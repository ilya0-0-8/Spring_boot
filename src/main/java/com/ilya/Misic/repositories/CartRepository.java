package com.ilya.Misic.repositories;

import com.ilya.Misic.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.id = :id")
    Cart findProductById(@Param("id") Long id);
    @Query("SELECT c FROM Cart c WHERE c.user.id= :userId")
    List<Cart> findByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c WHERE c.user.id= :userId AND c.product.id = :productId")
    List<Cart> findByProductIdAndUserId(@Param("productId") Long productId, @Param("userId") Long userId);

    void deleteByProductId(Long productId);
}

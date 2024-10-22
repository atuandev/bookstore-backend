package com.iuh.repository;

import com.iuh.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    @Query("SELECT od FROM OrderDetail od WHERE od.order.id = ?1")
    Optional<List<OrderDetail>> findAllByOrderId(String orderId);
}

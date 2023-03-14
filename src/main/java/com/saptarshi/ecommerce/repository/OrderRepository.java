package com.saptarshi.ecommerce.repository;

import com.saptarshi.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}

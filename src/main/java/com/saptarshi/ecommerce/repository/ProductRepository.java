package com.saptarshi.ecommerce.repository;

import com.saptarshi.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByUpdatedAtGreaterThan(Timestamp lastUpDate);

}

package com.saptarshi.ecommerce.repository;

import com.saptarshi.ecommerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {

    Optional<Seller> findByUserEmail(String email);

}

package com.saptarshi.ecommerce.repository;

import com.saptarshi.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}

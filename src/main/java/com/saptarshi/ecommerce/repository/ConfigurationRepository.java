package com.saptarshi.ecommerce.repository;

import com.saptarshi.ecommerce.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigurationRepository extends JpaRepository<Configuration,Long> {

    Optional<Configuration> findByName(String name);

}

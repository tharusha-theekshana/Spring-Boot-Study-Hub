package com.example.spring_boot_study_hub.Repositories;

import com.example.spring_boot_study_hub.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
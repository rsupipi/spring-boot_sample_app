package com.example.myProject.myApp.repository;

import com.example.myProject.myApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Boolean existsByName(String name);

    List<Product> findByCategoryIdAndStatus(Long categoryId, String status);

    List<Product> findByPriceGreaterThanEqualAndStatus(Double price, String status);

}

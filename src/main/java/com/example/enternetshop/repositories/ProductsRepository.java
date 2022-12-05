package com.example.enternetshop.repositories;


import com.example.enternetshop.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository <Product, Long>, JpaSpecificationExecutor<Product> {
}

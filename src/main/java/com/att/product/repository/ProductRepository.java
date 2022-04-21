package com.att.product.repository;

import java.util.List;
import java.util.Optional;

import com.att.product.model.Product;
public interface ProductRepository {
  
    Optional<Product> findById(Integer id);
    List<Product> findAll();
    boolean update(Product product);
    Product save(Product product);
    boolean delete(Integer id);
}

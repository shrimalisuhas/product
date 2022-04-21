package com.att.product.service;

import java.util.List;
import java.util.Optional;

import com.att.product.model.Product;

public interface ProductService {    
	Optional<Product> findById(Integer id);

     List<Product> findAll();   
       
    Product save(Product product);
    
    boolean update(Product product);


    
  
}

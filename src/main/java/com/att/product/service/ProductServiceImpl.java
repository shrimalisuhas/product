package com.att.product.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.att.product.model.Product;
import com.att.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        logger.info("Find product with id: {}", id);
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        logger.info("Find all products");
        return productRepository.findAll();
    }

  

    @Override
    public Product save(Product product) {
        product.setVersion(1);

        logger.info("Save product to the database: {}", product);
        return productRepository.save(product);
    }

    @Override
    public boolean update(Product product) {
        logger.info("Update product: {}", product);
        return productRepository.update(product);
    }

  
}

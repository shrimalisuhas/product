package com.att.product.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ObjectUtils;

import com.att.product.model.Product;
import java.util.List;
import java.util.Optional;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void testFindByIdSuccess() {
                Product product = repository.save(new Product(null,"Prodeuict",100,1));

        Assertions.assertTrue(!ObjectUtils.isEmpty(product), "Product should be found");
        Assertions.assertEquals(1, product.getId().intValue(), "Product ID should be 1");
        Assertions.assertEquals("Prodeuict", product.getName(), "Product name should be \"Product 5\"");
        Assertions.assertEquals(100, product.getQuantity().intValue(), "Product quantity should be 100");
        Assertions.assertEquals(1, product.getVersion().intValue(), "Product version should be 1");
    }
   
    @Test
    void testFindAll() {
        List<Product> products = repository.findAll();
        Assertions.assertEquals(2, products.size(), "We should not have any products in our database");
    }

    @Test
    void testSave() {
        Product product = new Product("Product 5", 5);
        product.setVersion(1);
        Product savedProduct = repository.save(product);

        Assertions.assertEquals("Product 5", savedProduct.getName());
        Assertions.assertEquals(5, savedProduct.getQuantity().intValue());
        Optional<Product> loadedProduct = repository.findById(savedProduct.getId());
        Assertions.assertTrue(loadedProduct.isPresent(), "Could not reload product from the database");
        Assertions.assertEquals("Product 5", loadedProduct.get().getName(), "Product name does not match");
        Assertions.assertEquals(5, loadedProduct.get().getQuantity().intValue(), "Product quantity does not match");
        Assertions.assertEquals(1, loadedProduct.get().getVersion().intValue(), "Product version is incorrect");
    }
 
    @Test
    void testUpdateSuccess() {
        Product product =repository.save(new Product(null,"Prodeuict",100,1));
        product.setName("Updated Product");
        boolean result  = repository.update(product);

        Assertions.assertTrue(result, "The product should have been updated");

        Optional<Product> loadedProduct = repository.findById(1);
        Assertions.assertTrue(loadedProduct.isPresent(), "Updated product should exist in the database");
        Assertions.assertEquals("Prodeuict", loadedProduct.get().getName(), "The product name does not match");
        Assertions.assertEquals(100, loadedProduct.get().getQuantity().intValue(), "The quantity should now be 100");
        Assertions.assertEquals(1, loadedProduct.get().getVersion().intValue(), "The version should now be 1");
    }
    
    


    

}
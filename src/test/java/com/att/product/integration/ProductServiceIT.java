package com.att.product.integration;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.att.product.model.Product;
import com.att.product.service.ProductService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({ SpringExtension.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProductServiceIT {

	@Autowired
	private ProductService productService;
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setup() {
		RestAssured.port = port;
	}
	
	public ProductServiceIT() {}

	@Test
	@DisplayName("GET /product/1")
	public void testGetProductByIdFound() throws Exception {
	
		Product prod = productService.save(new Product(null,"Product Name",1000,1));
		Optional<Product> result = productService.findById(prod.getId());
		Product res = result.get();
		Assertions.assertEquals(prod.getId(), res.getId());

	}

	@Test
	@DisplayName("POST /product - Success")
	public void testCreateProduct() throws Exception {
				Product product = productService.save(new Product(null,"Prodeuict",100,1));
		assertNotNull(product);
		
	}

	@Test
	@DisplayName("PUT /product/2 - Success")
	public void testProductPutSuccess() throws Exception {
				Product saved = productService.save(new Product(null,"Product Name",1000,1));
		saved.setName("Updated Name");
		saved.setQuantity(10);
		boolean prod = productService.update(saved);
		assertTrue(prod);
	}


	@Test
	@DisplayName("DELETE /product/1 - Success")
	public void testProductDeleteSuccess() throws Exception {
				Product product =  productService.save(new Product(null,"Product Name",1000,1));
		boolean result = productService.delete(product.getId());
		assertTrue(result);
	}


}

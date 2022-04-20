package com.att.product;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.att.product.controller.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductApplicationTests {

	@Autowired
	ProductController productController;
	@Test
	void contextLoads() {
		assertNotNull(productController);
	}

}

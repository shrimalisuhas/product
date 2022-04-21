package com.att.product.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static java.util.Arrays.asList;


import com.att.product.model.Product;
import com.att.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
	@MockBean
	private ProductService service;

	private ProductController productController;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET /product/1 - Found")
	void testGetProductByIdFound() throws Exception {
		Product mockProduct = new Product(1, "Product Name", 10, 1);
		doReturn(Optional.of(mockProduct)).when(service).findById(1);
		mockMvc.perform(get("/product/{id}", 1))

				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Product Name")))
				.andExpect(jsonPath("$.quantity", is(10))).andExpect(jsonPath("$.version", is(1)));
	}

	@Test
	@DisplayName("GET/product")
	void testGetAllProducts() throws Exception {
		
		when(service.findAll()).
		thenReturn(asList(new Product(1, "Product Name1", 10, 1)));
		
		this.mockMvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Product Name1")))
				.andExpect(jsonPath("$[0].quantity", is(10)))
				.andExpect(jsonPath("$[0].version", is(1)));
	}

	@Test
	@DisplayName("GET /product/1 - Not Found")
	void testGetProductByIdNotFound() throws Exception {
		doReturn(Optional.empty()).when(service).findById(1);

		mockMvc.perform(get("/product/{id}", 1))
				.andExpect(status().isNotFound());
		productController = new ProductController(service);
		var response = productController.getProduct(10);
		assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
	}

	@Test
	@DisplayName("POST /product - Success")
	void testCreateProduct() throws Exception {
				Product postProduct = new Product("Product Name", 10);
		Product mockProduct = new Product(1, "Product Name", 10, 1);
		doReturn(mockProduct).when(service).save(any());

		mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(asJsonString(postProduct)))

				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Product Name")))
				.andExpect(jsonPath("$.quantity", is(10))).andExpect(jsonPath("$.version", is(1)));
	}

	

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	@DisplayName("PUT /product/1 - Success")
	void testProductPutSuccess() throws Exception {
		Product putProduct = new Product("Product Name", 10);
		Product mockProduct = new Product(1, "Product Name", 10, 1);
		doReturn(Optional.of(mockProduct)).when(service).findById(1);
		doReturn(true).when(service).update(any());

		mockMvc.perform(put("/product/{id}", 1).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.IF_MATCH, 1)
				.content(asJsonString(putProduct)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Product Name")))
				.andExpect(jsonPath("$.quantity", is(10))).andExpect(jsonPath("$.version", is(2)));
	}


}

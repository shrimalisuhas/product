package com.att.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.att.product.model.Product;
import com.att.product.service.ProductService;

import java.util.Optional;

@RestController
public class ProductController {


	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer id) {

		Optional<Product> prod = productService.findById(id);
		Product product;
		if (prod.isPresent()) {
			product = prod.get();
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);

	}

	@GetMapping("/products")
	public Iterable<Product> getProducts() {
		return productService.findAll();
	}

	@PostMapping("/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product newProduct = productService.save(product);
		return new ResponseEntity<>((newProduct), HttpStatus.OK);
	}


	
}

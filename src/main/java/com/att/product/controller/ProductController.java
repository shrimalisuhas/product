package com.att.product.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.att.product.model.Product;
import com.att.product.service.ProductService;

import java.util.Optional;

@RestController
public class ProductController {

	private static final Logger logger = LogManager.getLogger(ProductController.class);

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


	@SuppressWarnings("unchecked")
	@PutMapping("/product/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Integer id,
			@RequestHeader("If-Match") Integer ifMatch) {
		Optional<Product> existingProduct = productService.findById(id);

		return (ResponseEntity<Product>) existingProduct.map(p -> {
			if (!p.getVersion().equals(ifMatch)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}

			p.setName(product.getName());
			p.setQuantity(product.getQuantity());
			p.setVersion(p.getVersion() + 1);

			productService.update(p);
			return new ResponseEntity<>((p), HttpStatus.OK);

		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Integer id) {

		logger.info("Deleting product with ID {}", id);

		Optional<Product> existingProduct = productService.findById(id);

		return existingProduct.map(p -> {
			if (productService.delete(p.getId())) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}).orElse(ResponseEntity.notFound().build());
	}
}

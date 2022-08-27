package com.learning.spring.es.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.spring.es.data.entity.Product;
import com.learning.spring.es.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products/repo")
	public ResponseEntity<Object> getAllProductsFromRepo() {
		List<Product> products = productService.getProductsWithRepo();
		if (products.isEmpty()) {
			return new ResponseEntity<>("No product found!", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@GetMapping("/products/repo/{productId}")
	public ResponseEntity<Object> getProductFromRepo(@PathVariable int productId) {
		Optional<Product> products = productService.getProductWithRepo(productId);
		if (products.isPresent()) {
			return new ResponseEntity<>(products, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No product found with given id " + productId, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/products/repo")
	public ResponseEntity<Object> createProductWithRepo(@RequestBody Product product) {
		return new ResponseEntity<>(productService.createProductWithRepo(product), HttpStatus.CREATED);
	}

	@DeleteMapping("/products/repo/{productId}")
	public ResponseEntity<Object> deleteProductWithRepo(@PathVariable int productId) {
		Optional<Product> product = productService.getProductWithRepo(productId);
		if (product.isPresent()) {
			productService.deleteProductWithRepo(product.get());
			return new ResponseEntity<>("Deleted product successfully!", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("No Product found with the given id " + productId, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/products")
	public ResponseEntity<Object> createProduct(@RequestBody Product product) {
		return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
	}
	
	@GetMapping("/products/{name}")
	public ResponseEntity<Object> findByProductName(@PathVariable String name) {
		return new ResponseEntity<>(productService.findByProductName(name), HttpStatus.OK);
	}
}

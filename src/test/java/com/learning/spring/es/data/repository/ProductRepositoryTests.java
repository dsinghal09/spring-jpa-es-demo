package com.learning.spring.es.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.learning.spring.es.data.entity.Product;

@SpringBootTest
public class ProductRepositoryTests {
	@Autowired
	private ProductESRepository productRepository;

	@Test
	public void test_GetEmployeeById_WithRepo() {
		Product product = stubProduct(100, "Guitar", "Guitar desc", "Music", 2000.0);
		product.setProductId(100);
		product = productRepository.save(product);
		Product productResponse = null;
		Optional<Product> productES = productRepository.findById(product.getProductId());
		if (productES.isPresent()) {
			productResponse = productES.get();
		}
		assertThat(productResponse).isNotNull();
		assertThat(productResponse.getProductName()).matches(product.getProductName());
	}

	private Product stubProduct(int productId, String productName, String description, String category, Double price) {
		return Product.builder().productId(productId).productName(productName).description(description).category(category).price(price)
				.build();
	}
}

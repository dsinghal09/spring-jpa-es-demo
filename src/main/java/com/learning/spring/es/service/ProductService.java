package com.learning.spring.es.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.learning.spring.es.data.constant.ProductConstants;
import com.learning.spring.es.data.entity.Product;
import com.learning.spring.es.data.repository.ProductESRepository;

@Service
public class ProductService {

	@Autowired
	private ProductESRepository productRepository;

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	public Iterable<Product> createBulkProductsWithRepo(List<Product> products) {
		return productRepository.saveAll(products);
	}

	public Product createProductWithRepo(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getProductsWithRepo() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(prod -> products.add(prod));
		return products;
	}
	
	public Optional<Product> getProductWithRepo(int prodId) {
		return productRepository.findById(prodId);
	}
	
	public void deleteProductWithRepo(Product product) {
		productRepository.delete(product);
	}

	public Product createProduct(Product product) {
		return elasticsearchOperations
				.save(product, IndexCoordinates.of(ProductConstants.PRODUCT_INDEX));
	}
	
	public List<Product> findByProductName(String prodName) {
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("productName", prodName);
		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(queryBuilder)
				.build();
		SearchHits<Product> productHits = elasticsearchOperations
				.search(searchQuery, Product.class, IndexCoordinates.of(ProductConstants.PRODUCT_INDEX));
		return productHits
				.get()
				.map(SearchHit::getContent)
				.collect(Collectors.toList());
	}

}

package com.learning.spring.es.data.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.learning.spring.es.data.entity.Product;

public interface ProductESRepository extends ElasticsearchRepository<Product, Integer> {
	List<Product> findByProductName(String name);

	List<Product> findByCategory(String category);

}

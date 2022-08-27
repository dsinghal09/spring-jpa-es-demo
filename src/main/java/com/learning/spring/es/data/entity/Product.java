package com.learning.spring.es.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Builder;
import lombok.Data;

@Document(indexName = "products")
@Data
@Builder
public class Product {
	@Id
	private int productId;
	
	@Field(type = FieldType.Text, name = "productName")
	private String productName;
	
	@Field(type = FieldType.Text, name = "description")
	private String description;
	
	@Field(type = FieldType.Text, name = "category")
	private String category;

	@Field(type = FieldType.Double, name = "price")
	private double price;
}

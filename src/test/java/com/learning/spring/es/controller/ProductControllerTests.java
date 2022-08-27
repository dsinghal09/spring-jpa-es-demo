package com.learning.spring.es.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.spring.es.data.entity.Product;
import com.learning.spring.es.service.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String URL = "/products";

	private static final String REPO_URL = "/repo";

	@Test
	void test_GetProductById_WithRepository_Success() throws Exception {
		Product mockedProduct = stubProduct("Product 1", "Product1 description", "ABC", 500.0);
		mockedProduct.setProductId(1);
		
		Optional<Product> Product = Optional.of(mockedProduct);
		when(productService.getProductWithRepo(Mockito.anyInt())).thenReturn(Product);
		mockMvc.perform(get(URL + REPO_URL + "/1")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("productId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("productName").value("Product 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("description").value("Product1 description"))
				.andExpect(MockMvcResultMatchers.jsonPath("category").value("ABC"))
				.andExpect(MockMvcResultMatchers.jsonPath("price").value(500.0))
				.andDo(print());
	}

	@Test
	void test_GetProductById_WithRepository_Failure() throws Exception {
		mockMvc.perform(get(URL + REPO_URL + "/2")).andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void test_CreateProduct_WithRepository() throws Exception {
		Product mockedProduct = stubProduct("Product 2", "Product2 description", "ABC", 1500.0);
		mockedProduct.setProductId(2);
		when(productService.createProductWithRepo(Mockito.any())).thenReturn(mockedProduct);
		mockMvc.perform(post(URL + REPO_URL).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockedProduct))).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("productId").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("productName").value("Product 2"))
				.andExpect(MockMvcResultMatchers.jsonPath("description").value("Product2 description"))
				.andExpect(MockMvcResultMatchers.jsonPath("category").value("ABC"))
				.andExpect(MockMvcResultMatchers.jsonPath("price").value(1500.0))
				.andDo(print());
	}

	@Test
	void test_DeleteProduct_WithRepository_Success() throws Exception {
		Product mockedProduct = stubProduct("Product 3", "Product3 description", "BCD", 150.0);
		mockedProduct.setProductId(3);
		Optional<Product> Product = Optional.of(mockedProduct);
		when(productService.getProductWithRepo(Mockito.anyInt())).thenReturn(Product);
		doNothing().when(productService).deleteProductWithRepo(Mockito.any());
		mockMvc.perform(delete(URL + REPO_URL + "/3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent())
				.andDo(print());
	}

	@Test
	void test_DeleteProduct_WithRepository_Failure() throws Exception {
		mockMvc.perform(delete(URL + REPO_URL + "/4").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andDo(print());
	}
	
	@Test
	void test_CreateProduct() throws Exception {
		Product mockedProduct = stubProduct("Product 4", "Product4 description", "BCD", 1120.0);
		mockedProduct.setProductId(2);
		when(productService.createProduct(Mockito.any())).thenReturn(mockedProduct);
		mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockedProduct))).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("productId").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("productName").value("Product 4"))
				.andExpect(MockMvcResultMatchers.jsonPath("description").value("Product4 description"))
				.andExpect(MockMvcResultMatchers.jsonPath("category").value("BCD"))
				.andExpect(MockMvcResultMatchers.jsonPath("price").value(1120.0))
				.andDo(print());
	}

	private Product stubProduct(String productName, String description, String category, Double price) {
		return Product.builder().productName(productName).description(description).category(category).price(price)
				.build();
	}
}

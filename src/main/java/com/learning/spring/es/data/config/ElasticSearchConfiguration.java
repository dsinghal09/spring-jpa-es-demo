package com.learning.spring.es.data.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.learning.spring.es.data.constant.ProductConstants;

@SuppressWarnings("deprecation")
@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfiguration {

	static final Logger log = LoggerFactory.getLogger(ElasticSearchConfiguration.class);

	@Value("${elasticsearch.host}")
	private String hostName;

	@Value("${elasticsearch.port}")
	private String port;

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchRestTemplate(client());
	}

	@Bean
	public RestHighLevelClient client() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo(hostName + ProductConstants.COLON + port).build();
		return RestClients.create(clientConfiguration).rest();
	}

}

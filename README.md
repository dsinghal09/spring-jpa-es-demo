# spring-jpa-es-demo

**Rest APIs** 

The app defines following CRUD APIs that connect to Oracle Database.

GET /employees

GET /employees/{id}

POST /employees

DELETE /employees/{id}

The app defines following CRUD APIs that connect to Elasticsearch with ElasticsearchRepository

GET /products/repo

GET /products/repo/{productId}

POST /products/repo

DELETE /products/repo/{productId}

The app also defines following CRUD APIs that connect to Elasticsearch with ElasticsearchOperations

GET /products/{name}

POST /products

**Rest APIs Testing** 

The app contains Unit testing for both Controller and Repository using Mockito.

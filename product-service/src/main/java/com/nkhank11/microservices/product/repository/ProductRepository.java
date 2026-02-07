package com.nkhank11.microservices.product.repository;

import com.nkhank11.microservices.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}

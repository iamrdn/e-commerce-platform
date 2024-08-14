package com.altimetrik.product.service;

import com.altimetrik.product.model.Product;
import com.altimetrik.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    private static final String TOPIC = "product_topic";

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        kafkaTemplate.send(TOPIC, savedProduct);
        return savedProduct;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}

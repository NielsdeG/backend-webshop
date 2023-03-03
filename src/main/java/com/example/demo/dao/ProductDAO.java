package com.example.demo.dao;

import com.example.demo.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductDAO {
    private final ProductRepository productRepository;

    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(UUID id){
        if (productRepository.existsById(id)){
            return productRepository.findById(id);
        }
        return null;

    }

    public void saveProduct(Product product){
        this.productRepository.save(product);
    }

    public void delete(UUID id){
        Optional<Product> obj = this.productRepository.findById(id);
        Product product = obj.get();
        this.productRepository.delete(product);
    }
}
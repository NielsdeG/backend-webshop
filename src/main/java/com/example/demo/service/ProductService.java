package com.example.demo.service;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts(){
        return this.productDAO.getAllProducts();
    }

    public Optional<Product> getProduct(UUID id){
        return this.productDAO.getProduct(id);
    }

    public void saveProduct(Product product){
        this.productDAO.saveProduct(product);
    }

    public void delete(UUID id){
        this.productDAO.delete(id);
    }

    public void createProduct(Product p) {
        Product product = new Product();
        product.setCategory(p.getCategory());
        product.setDescription(p.getDescription());
        product.setImage(p.getImage());
        product.setTitle(p.getTitle());
        product.setPrice(p.getPrice());
        this.productDAO.saveProduct(product);
    }
}

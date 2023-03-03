package com.example.demo.controller;

import com.example.demo.helper.ResponseHandler;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@Controller
@RequestMapping(path="api/v1/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts(){
        List<Product> products = this.productService.getAllProducts();
        if(products.isEmpty()){
            return ResponseHandler.generateResponse("geen producten gevonden", HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse("succesvol producten gevonden", HttpStatus.OK, products);
    }

    @GetMapping(params="id")
    public ResponseEntity<Object> getProduct(@RequestParam UUID id){
        Optional<Product> product = this.productService.getProduct(id);
        if(product.isEmpty()){
            return ResponseHandler.generateResponse("geen product gevonden", HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse("succesvol producten gevonden", HttpStatus.OK, product.get());
    }

    @PutMapping("/save")
    public ResponseEntity<Object> updateProduct(@RequestBody Product newProduct){
        Optional<Product> oldProduct = this.productService.getProduct(newProduct.getId());
        if (oldProduct.isPresent()){
            this.productService.saveProduct(newProduct);
            return ResponseHandler.generateResponse("product succsvol geupodate", HttpStatus.OK, newProduct);
        }
        return ResponseHandler.generateResponse("fout in het updaten van het product", HttpStatus.BAD_REQUEST, null);
    }

    @DeleteMapping(path = "/delete" ,params="id")
    public ResponseEntity<Object> deleteProduct(@RequestParam UUID id){
        Optional<Product> product = this.productService.getProduct(id);
        if (product.isPresent()){
            this.productService.delete(id);
            return ResponseHandler.generateResponse("product succsvol gedeleted", HttpStatus.OK, null);
        }
        return ResponseHandler.generateResponse("fout in het updaten van het product", HttpStatus.BAD_REQUEST, null);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        this.productService.createProduct(product);
        return ResponseHandler.generateResponse("product gecreerd", HttpStatus.OK, null);
    }


}

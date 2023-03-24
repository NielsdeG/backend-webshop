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

    @PutMapping("/save")
    public ResponseEntity<Object> updateProduct(@RequestBody Product newProduct){
        System.out.println("lol");
        Optional<Product> oldProduct = this.productService.getProduct(newProduct.getId());
        if (oldProduct.isPresent()){
            this.productService.saveProduct(newProduct);
            return ResponseHandler.generateResponse("product succsvol geupodate", HttpStatus.OK, newProduct);
        }
        return ResponseHandler.generateResponse("fout in het updaten van het product", HttpStatus.BAD_REQUEST, null);
    }


}

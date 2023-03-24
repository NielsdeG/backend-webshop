package com.example.demo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name= "title")
    private String title;

    @Column(name="price")
    private float price;

    @Column(name="category")
    private String category;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name="image")
    private String image;

    public Product(UUID id, String title, float price, String category, String description, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
    }

    public Product() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

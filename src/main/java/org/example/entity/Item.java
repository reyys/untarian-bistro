package org.example.entity;

import org.example.Utility;

public class Item {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private int stock;
    private final String createdAt;
    private String updatedAt;

    public Item() {
        this.createdAt = Utility.getCurrentTimestamp();
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public Item(int id, String name, double price, String description, String imageUrl, int stock, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Item(int id, String name, double price, String description, String imageUrl, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.createdAt = Utility.getCurrentTimestamp();
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}

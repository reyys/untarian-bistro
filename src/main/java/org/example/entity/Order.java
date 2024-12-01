package org.example.entity;

import org.example.Utility;

public class Order {
    private int id;
    private double total_price;
    private int table_number;
    private final String created_at;
    private String updated_at;

    // Default constructor
    public Order() {
        this.created_at = Utility.getCurrentTimestamp();
        this.updated_at = Utility.getCurrentTimestamp();
    }

    // Constructor with parameters for total_price and table_number
    public Order(double total_price, int table_number) {
        this.total_price = total_price;
        this.table_number = table_number;
        this.created_at = Utility.getCurrentTimestamp();
        this.updated_at = Utility.getCurrentTimestamp();
    }

    // Constructor with all fields
    public Order(int id, double total_price, int table_number, String created_at, String updated_at) {
        this.id = id;
        this.total_price = total_price;
        this.table_number = table_number;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.updated_at = Utility.getCurrentTimestamp();
    }

    public double getTotalPrice() {
        return total_price;  // Adjusted to match the field name
    }

    public void setTotalPrice(double total_price) {
        this.total_price = total_price;
        this.updated_at = Utility.getCurrentTimestamp();
    }

    public int getTableNumber() {
        return table_number;  // Adjusted to match the field name
    }

    public void setTableNumber(int table_number) {
        this.table_number = table_number;
        this.updated_at = Utility.getCurrentTimestamp();
    }

    public String getCreatedAt() {
        return this.created_at;  // Adjusted to match the field name
    }

    public String getUpdatedAt() {
        return this.updated_at;  // Adjusted to match the field name
    }
}

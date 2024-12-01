package org.example.entity;

public class OrderItems {
    private int id;
    private int order_id;
    private int item_id;

    // Default constructor
    public OrderItems() {}

    // Constructor with parameters
    public OrderItems(int order_id, int item_id) {
        this.order_id = order_id;
        this.item_id = item_id;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return order_id;
    }

    public void setOrderId(int order_id) {
        this.order_id = order_id;
    }

    public int getItemId() {
        return item_id;
    }

    public void setItemId(int item_id) {
        this.item_id = item_id;
    }
}

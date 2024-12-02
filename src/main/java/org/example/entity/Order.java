package org.example.entity;

import org.example.Utility;

import java.util.List;

public class Order {
    private int id;
    private List<OrderItem> orderItems;
    private double totalPrice;
    private int tableNumber;
    private final String createdAt;
    private String updatedAt;

    public Order() {
        this.createdAt = Utility.getCurrentTimestamp();
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public Order(int tableNumber) {
        this.tableNumber = tableNumber;
        this.createdAt = Utility.getCurrentTimestamp();
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public Order(int tableNumber, List<OrderItem> orderItems) {
        this.tableNumber = tableNumber;
        this.orderItems = orderItems;
        this.createdAt = Utility.getCurrentTimestamp();
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public Order(double totalPrice, int tableNumber, List<OrderItem> orderItems) {
        this.totalPrice = totalPrice;
        this.tableNumber = tableNumber;
        this.orderItems = orderItems;
        this.createdAt = Utility.getCurrentTimestamp();
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public Order(int id, List<OrderItem> orderItems, double totalPrice, int tableNumber, String created_at,
                 String updated_at) {
        this.id = id;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.tableNumber = tableNumber;
        this.createdAt = created_at;
        this.updatedAt = updated_at;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double total_price) {
        this.totalPrice = total_price;
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        this.updatedAt = Utility.getCurrentTimestamp();
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderItems=" + orderItems +
                ", totalPrice=" + totalPrice +
                ", tableNumber=" + tableNumber +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}

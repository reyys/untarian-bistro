package org.example.entity;

public class OrderItem {
    private int id;
    private int orderId;
    private int itemId;

    public OrderItem() {
    }

    public OrderItem(int orderId, int itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }

    public OrderItem(int id, int orderId, int itemId) {
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "OrderItem [id=" + id + ", orderId=" + orderId + ", itemId=" + itemId + "]";
    }

}
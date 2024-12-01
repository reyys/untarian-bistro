package org.example.repository;

import org.example.entity.Item;
import org.example.entity.Order;
import org.example.entity.OrderItems;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

import static org.example.repository.Database.db;

public class OrderRepository implements BaseRepository<Order> {

    @Override
    public Order save(Order order) {
        String query = "INSERT INTO orders (total_price, table_number, created_at, updated_at) " +
                "VALUES (:total_price, :table_number, :created_at, :updated_at)";

        try (Connection con = db.open()) {
            int orderId = con.createQuery(query, true)
                    .addParameter("total_price", order.getTotalPrice())
                    .addParameter("table_number", order.getTableNumber())
                    .addParameter("created_at", order.getCreatedAt())
                    .addParameter("updated_at", order.getUpdatedAt())
                    .executeUpdate()
                    .getKey(Integer.class);

            // Save order items in the OrderItems table
//            saveOrderItems(orderId, order.getItems());

//            return findById(orderId);
            return null;

        }
    }

    private void saveOrderItems(int orderId, List<Item> items) {
        String query = "INSERT INTO order_items (order_id, item_id) VALUES (:order_id, :item_id)";

        try (Connection con = db.open()) {
            for (Item item : items) {
                con.createQuery(query)
                        .addParameter("order_id", orderId)
                        .addParameter("item_id", item.getId())
                        .executeUpdate();
            }
        }
    }

    @Override
    public List<Order> findAll() {
        String query = "SELECT * FROM orders";

        try (Connection con = db.open()) {
            List<Order> orders = con.createQuery(query).executeAndFetch(Order.class);
//            // Populate items for each order
//            for (Order order : orders) {
//                order.setItems(findItemsByOrderId(order.getId()));
//            }
            return orders;
        }
    }

    @Override
    public Order findById(int id) {
        String query = "SELECT * FROM orders WHERE id = :id";

        try (Connection con = db.open()) {
            Order order = con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Order.class);

//            if (order != null) {
//                order.setItems(findItemsByOrderId(id));  // Fetch associated items
//            }

            return order;
        }
    }

    private List<Item> findItemsByOrderId(int orderId) {
        String query = "SELECT i.* FROM items i INNER JOIN order_items oi ON i.id = oi.item_id WHERE oi.order_id = :order_id";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                    .addParameter("order_id", orderId)
                    .executeAndFetch(Item.class);
        }
    }

    @Override
    public void update(Order order) {
        String query = "UPDATE orders SET total_price = :total_price, table_number = :table_number, updated_at = :updated_at WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                    .addParameter("total_price", order.getTotalPrice())
                    .addParameter("table_number", order.getTableNumber())
                    .addParameter("updated_at", order.getUpdatedAt())
                    .addParameter("id", order.getId())
                    .executeUpdate();

            // Remove existing order items and save the updated ones
            deleteOrderItems(order.getId());
//            saveOrderItems(order.getId(), order.getItems());
        } catch (Sql2oException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteOrderItems(int orderId) {
        String query = "DELETE FROM order_items WHERE order_id = :order_id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                    .addParameter("order_id", orderId)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM orders WHERE id = :id";

        try (Connection con = db.open()) {
            deleteOrderItems(id);  // Delete associated order items first
            con.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public int getLastId() {
        String query = "SELECT LAST_INSERT_ID()";

        try (Connection con = db.open()) {
            return con.createQuery(query).executeScalar(Integer.class);
        }
    }
}

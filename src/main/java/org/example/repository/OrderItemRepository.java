package org.example.repository;

import org.example.entity.OrderItem;
import org.sql2o.Connection;

import java.util.List;

import static org.example.repository.Database.db;

public class OrderItemRepository implements BaseRepository<OrderItem> {

    @Override
    public OrderItem save(OrderItem orderItem) {
        String query = "INSERT INTO order_items (order_id, item_id) " +
                "VALUES (:orderId, :itemId)";

        try (Connection con = db.open()) {
            int id = con.createQuery(query, true)
                    .bind(orderItem)
                    .executeUpdate()
                    .getKey(Integer.class);

            return findById(id);
        }
    }

    public List<OrderItem> findByOrderId(int orderId) {
        String query = "SELECT * FROM order_items WHERE order_id = :orderId";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                    .addParameter("orderId", orderId)
                    .executeAndFetch(OrderItem.class);
        }
    }

    @Override
    public List<OrderItem> findAll() {
        String query = "SELECT * FROM order_items";

        try (Connection con = db.open()) {
            return con.createQuery(query).executeAndFetch(OrderItem.class);
        }
    }

    @Override
    public OrderItem findById(int id) {
        String query = "SELECT * FROM order_items WHERE id = :id";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(OrderItem.class);
        }
    }

    @Override
    public void update(OrderItem entity) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public int getLastId() {
        String query = "SELECT LAST_INSERT_ID()";

        try (Connection con = db.open()) {
            return con.createQuery(query).executeScalar(Integer.class);
        }
    }
}
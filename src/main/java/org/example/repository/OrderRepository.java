package org.example.repository;

import org.example.entity.Order;
import org.sql2o.Connection;

import java.util.List;

import static org.example.repository.Database.db;

public class OrderRepository implements BaseRepository<Order> {

    @Override
    public Order save(Order order) {
        String query = "INSERT INTO orders (total_price, table_number, created_at, updated_at) " +
                "VALUES (:totalPrice, :tableNumber, :createdAt, :updatedAt)";

        try (Connection con = db.open()) {
            int id = con.createQuery(query, true)
                    .bind(order)
                    .executeUpdate()
                    .getKey(Integer.class);

            return findById(id);
        }
    }

    @Override
    public List<Order> findAll() {
        String query = "SELECT * FROM orders";

        try (Connection con = db.open()) {
            return con.createQuery(query).executeAndFetch(Order.class);
        }
    }

    @Override
    public Order findById(int id) {
        String query = "SELECT * FROM orders WHERE id = :id";

        try (Connection con = db.open()) {
            return con.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Order.class);
        }
    }

    @Override
    public void update(Order entity) {
        String query = "UPDATE orders SET total_price = :totalPrice, table_number = :tableNumber, updated_at = " +
                ":updatedAt "
                +
                "WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                    .bind(entity)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM orders WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public int getLastId() {
        String query = "SELECT LAST_INSERT_ID()";

        try (Connection con = db.open()) {
            return con.createQuery(query).executeScalar(Integer.class);
        }
    }
}

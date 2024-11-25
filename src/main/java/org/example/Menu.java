package org.example;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.*;
import java.util.List;

class Menu {
    private Sql2o sql2o;

    public Menu() throws Exception {
        this.sql2o = new Sql2o("jdbc:mysql://localhost:3306/UntarianBistro", "root", "");
    }

    public boolean isConnected() {
        try (Connection conn = sql2o.open()) {
            return conn != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Create (Insert a new menu item)
    public int insertMenuItem(Item item) {
        String query = "INSERT INTO menu(name, price, description, image_url, created_at, updated_at, stock) " +
                "VALUES (:name, :price, :description, :imageUrl, :createdAt, :updatedAt, :stock)";
        try (Connection conn = sql2o.open()) {
            int id = conn.createQuery(query, true)
                    .addParameter("name", item.getName())
                    .addParameter("price", item.getPrice())
                    .addParameter("description", item.getDescription())
                    .addParameter("imageUrl", item.getImageUrl())
                    .addParameter("createdAt", item.getCreatedAt())
                    .addParameter("updatedAt", item.getUpdatedAt())
                    .addParameter("stock", item.getStock())
                    .executeUpdate()
                    .getKey(Integer.class);
            return id;
        }
    }

    // Read (Get all menu items)
    public List<Item> selectAllMenuItems() {
        String query = "SELECT * FROM menu";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query).executeAndFetch(Item.class);
        }
    }

    // Read (Fetch a menu item by ID)
    public Item selectMenuItemById(int id) {
        String query = "SELECT * FROM menu WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Item.class);
        }
    }

    // Update (Modify a menu item)
    public void updateMenuItem(Item item) {
        String query = "UPDATE menu SET name = :name, price = :price, description = :description, image_url = :imageUrl, " +
                "updated_at = :updatedAt, stock = :stock WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(query)
                    .addParameter("name", item.getName())
                    .addParameter("price", item.getPrice())
                    .addParameter("description", item.getDescription())
                    .addParameter("imageUrl", item.getImageUrl())
                    .addParameter("updatedAt", Utility.getCurrentTimestamp())
                    .addParameter("stock", item.getStock())
                    .addParameter("id", item.getId())
                    .executeUpdate();
        }
    }


    // Delete (Remove a menu item)
    public void deleteMenuItemById(int id) {
        String query = "DELETE FROM menu WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    // Get the last inserted ID
    public int getLastId() {
        String query = "SELECT LAST_INSERT_ID() as id";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .executeScalar(Integer.class);
        }
    }
}

package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Menu {
    private Connection conn;

    public Menu() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UntarianBistro", "root", "");
    }

    public boolean isConnected() {
        return (this.conn != null);
    }

    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create (Insert a new menu item)
    public void insertMenuItem(Item item) throws SQLException {
        String query = "INSERT INTO menu(name, price, description, image_url, created_at, updated_at, stock) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = this.conn.prepareStatement(query);
        stmt.setString(1, item.getName());
        stmt.setDouble(2, item.getPrice());
        stmt.setString(3, item.getDescription());
        stmt.setString(4, item.getImageUrl());
        stmt.setString(5, item.getCreatedAt());
        stmt.setString(6, item.getUpdatedAt());
        stmt.setInt(7, item.getStock());
        stmt.executeUpdate();
    }

    // Read (Get all menu items)
    public List<Item> selectAllMenuItems() throws SQLException {
        String query = "SELECT * FROM menu";
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Item> items = new ArrayList<>();
        while (rs.next()) {
            items.add(new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getString("image_url"),
                    rs.getInt("stock"),
                    rs.getString("created_at"),
                    rs.getString("updated_at")
            ));
        }
        return items;
    }

    // Read (Fetch a menu item by ID)
    public Item selectMenuItemById(int id) throws SQLException {
        String query = "SELECT * FROM menu WHERE id = ?";
        PreparedStatement stmt = this.conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getString("image_url"),
                    rs.getInt("stock"),
                    rs.getString("created_at"),
                    rs.getString("updated_at")
            );
        } else {
            return null;
        }
    }

    // Update (Modify a menu item)
    public void updateMenuItem(Item item) throws SQLException {
        String query = "UPDATE menu SET name = ?, price = ?, description = ?, image_url = ?, updated_at = ?, stock = ? WHERE id = ?";
        PreparedStatement stmt = this.conn.prepareStatement(query);
        stmt.setString(1, item.getName());
        stmt.setDouble(2, item.getPrice());
        stmt.setString(3, item.getDescription());
        stmt.setString(4, item.getImageUrl());
        stmt.setString(5, Utility.getCurrentTimestamp());
        stmt.setInt(6, item.getStock());
        stmt.setInt(7, item.getId());
        stmt.executeUpdate();
    }

    // Delete (Remove a menu item)
    public void deleteMenuItemById(int id) throws SQLException {
        String query = "DELETE FROM menu WHERE id = ?";
        PreparedStatement stmt = this.conn.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.execute();
    }

    // Get the last inserted ID
    public int getLastId() throws SQLException {
        String idQuery = "SELECT LAST_INSERT_ID()";
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(idQuery);
        if (rs.next()) {
            return rs.getInt(1);  // Return the last inserted ID
        } else {
            throw new SQLException("Failed to retrieve generated ID");
        }
    }
}

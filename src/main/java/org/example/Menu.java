package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    // Helper method to get the current timestamp
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Create (Insert a new menu item)
    public void insertMenuItem(String name, double price, String description, String imageUrl, int stock) throws SQLException {
        String query = "INSERT INTO menu(name, price, description, image_url, created_at, stock) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = this.conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setDouble(2, price);
        stmt.setString(3, description);
        stmt.setString(4, imageUrl);
        stmt.setString(5, getCurrentTimestamp());
        stmt.setInt(6, stock);
        stmt.execute();
    }

    // Read (Fetch a menu item by ID)
    public void selectMenuItem(int id) throws SQLException {
        String query = "SELECT * FROM menu WHERE id = ?";
        PreparedStatement stmt = this.conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Price: " + rs.getDouble("price"));
            System.out.println("Description: " + rs.getString("description"));
            System.out.println("Image URL: " + rs.getString("image_url"));
            System.out.println("Created At: " + rs.getString("created_at"));
            System.out.println("Stock: " + rs.getInt("stock"));
        }
    }

    // Update (Modify a menu item)
    public void updateMenuItem(int id, String name, double price, String description, String imageUrl, int stock) throws SQLException {
        String query = "UPDATE menu SET name = ?, price = ?, description = ?, image_url = ?, updated_at = ?, stock = ? WHERE id = ?";
        PreparedStatement stmt = this.conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setDouble(2, price);
        stmt.setString(3, description);
        stmt.setString(4, imageUrl);
        stmt.setString(5, getCurrentTimestamp());
        stmt.setInt(6, stock);
        stmt.setInt(7, id);
        stmt.execute();
    }

    // Delete (Remove a menu item)
    public void deleteMenuItem(int id) throws SQLException {
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

package org.example.repository;

import org.example.entity.Item;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

import static org.example.repository.Database.db;

public class ItemRepository implements BaseRepository<Item> {

    public Item save(Item item) {
        String query = "INSERT INTO menu" + "(name, price, description, image_url, stock, created_at, updated_at)" +
                "VALUES (:name, :price, :description, :imageUrl, :stock, :createdAt, :updatedAt)";

        try (Connection con = db.open()) {
            int id = con.createQuery(query, true).bind(item).executeUpdate().getKey(Integer.class);

            return findById(id);
        }
    }

    // Read (Get all menu items)
    public List<Item> findAll() {
        String query = "SELECT * FROM menu";

        try (Connection con = db.open()) {
            return con.createQuery(query).executeAndFetch(Item.class);
        }
    }

    // Read (Fetch a menu item by ID)
    public Item findById(int id) {
        String query = "SELECT * FROM menu WHERE id = :id";

        try (Connection con = db.open()) {
            return con.createQuery(query).addParameter("id", id).executeAndFetchFirst(Item.class);
        }
    }

    // Update (Modify a menu item)
    public void update(Item item) {
        String query = "UPDATE menu SET name = :name, price = :price, description = :description, image_url = " +
                ":imageUrl, stock = :stock, updated_at = :updatedAt WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query).bind(item).executeUpdate();
        } catch (Sql2oException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Delete (Remove a menu item)
    public void deleteById(int id) {
        String query = "DELETE FROM menu WHERE id = :id";

        try (Connection con = db.open()) {
            con.createQuery(query).addParameter("id", id).executeUpdate();
        }
    }

    // Get the last inserted ID
    public int getLastId() {
        String query = "SELECT LAST_INSERT_ID()";

        try (Connection con = db.open()) {
            return con.createQuery(query).executeScalar(Integer.class);
        }
    }
}

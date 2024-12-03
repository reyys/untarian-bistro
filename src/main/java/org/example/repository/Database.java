package org.example.repository;

import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

public class Database {
    static final Sql2o db;

    static {
        db = new Sql2o("jdbc:mysql://localhost:3306/UntarianBistro", "root", "");
        Map<String, String> colMaps = new HashMap<String, String>();
        colMaps.put("image_url", "imageUrl");
        colMaps.put("total_price", "totalPrice");
        colMaps.put("table_number", "tableNumber");
        colMaps.put("order_id", "orderId");
        colMaps.put("item_id", "itemId");
        colMaps.put("created_at", "createdAt");
        colMaps.put("updated_at", "updatedAt");
        db.setDefaultColumnMappings(colMaps);
    }
}

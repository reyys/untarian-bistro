package org.example.repository;

import org.sql2o.Sql2o;

public class Database {
    static final Sql2o db;

    static {
        db = new Sql2o("jdbc:mysql://localhost:3306/CorporateXYZ", "root", "changeme");
    }
}

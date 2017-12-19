package main.java.ua.nure.koroliuk.db;

import java.sql.Connection;


public interface ConnectionFactory {
    Connection createConnection() throws DatabaseException;
}

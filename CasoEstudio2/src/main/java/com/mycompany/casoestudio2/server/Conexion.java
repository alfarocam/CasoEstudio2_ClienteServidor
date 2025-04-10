package com.mycompany.casoestudio2.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static final String URL = "x";
    public static final String USER = "root"; // Cambia por tu usuario de MySQL
    public static final String PASSWORD = "x"; // Cambia por tu contraseña de MySQL

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a la base de datos!");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conn;
    }
}
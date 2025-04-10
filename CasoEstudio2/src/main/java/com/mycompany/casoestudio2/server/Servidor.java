/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casoestudio2.server;
import com.mycompany.casoestudio2.farm.Animal;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
/**
 *
 * @author CamilaAlfaro
 */
public class Servidor {

    // Puerto del servidor
    private static final int PUERTO = 5000;
    // Máximo de clientes permitidos
    private static final int MAX_CLIENTES = 5;
    // Lista de manejadores de clientes conectados
    private static final List<ManejadorCliente> clientesConectados = new ArrayList<>();
    // Conexión a la base de datos
    private static Connection conexionBD;

    public static void main(String[] args) {
        // Inicializar la conexión a la base de datos
        inicializarConexionBD();
        
        // Pool de hilos para manejar múltiples clientes concurrentemente
        ExecutorService ejecutor = Executors.newFixedThreadPool(MAX_CLIENTES);
        
        // Creación del socket del servidor
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor del Rancho 'El buen bistec' iniciado en el puerto: " + PUERTO);

            while (true) {
                try {
                    // Acepta la conexión del cliente
                    Socket socketCliente = servidor.accept();
                    System.out.println("Nuevo cliente conectado: " + socketCliente.getInetAddress());
                    
                    // Se le asigna un hilo al cliente para manejarlo
                    ClientHandler manejador = new ClientHandler(socketCliente);
                    clientesConectados.add(manejador);
                    ejecutor.execute(manejador);
                } catch (IOException e) {
                    System.out.println("Error al aceptar conexión: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra el pool de hilos cuando el servidor termina
            ejecutor.shutdown();
            // Cierra la conexión a la base de datos
            cerrarConexionBD();
        }
    }
    
    /**
     * Inicializa la conexión a la base de datos
     */
    private static void inicializarConexionBD() {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión con la base de datos
            conexionBD = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/rancho_ganadero", 
                "usuario", // Reemplazar con usuario real
                "password" // Reemplazar con contraseña real
            );
            System.out.println("Conexión a la base de datos establecida correctamente.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Termina el programa si no puede conectar a la BD
        }
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    private static void cerrarConexionBD() {
        try {
            if (conexionBD != null && !conexionBD.isClosed()) {
                conexionBD.close();
                System.out.println("Conexión a la base de datos cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión con la base de datos: " + e.getMessage());
        }
    }
}
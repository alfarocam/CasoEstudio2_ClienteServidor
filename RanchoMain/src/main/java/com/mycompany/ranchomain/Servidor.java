/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ranchomain;
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
                    ManejadorCliente manejador = new ManejadorCliente(socketCliente);
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

    /**
     * Clase que maneja la comunicación con cada cliente
     */
    public static class ManejadorCliente implements Runnable {
        private Socket socket;
        private ObjectOutputStream salida;
        private ObjectInputStream entrada;
        private String nombreUsuario;
        private boolean conectado = true;
        
        // DAOs para acceso a datos
        private AnimalDAO animalDAO;
        private EventoDAO eventoDAO;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
            this.animalDAO = new AnimalDAO(conexionBD);
            this.eventoDAO = new EventoDAO(conexionBD);
        }

        @Override
        public void run() {
            try {
                // Inicializar streams de entrada y salida
                salida = new ObjectOutputStream(socket.getOutputStream());
                salida.flush();
                entrada = new ObjectInputStream(socket.getInputStream());
                
                // Autenticación del usuario
                procesarAutenticacion();
                
                // Bucle principal para procesar solicitudes
                while (conectado) {
                    try {
                        // Recibir solicitud del cliente
                        Solicitud solicitud = (Solicitud) entrada.readObject();
                        procesarSolicitud(solicitud);
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error al recibir objeto: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println("Error de conexión con cliente: " + e.getMessage());
            } finally {
                desconectar();
            }
        }
        
        /**
         * Procesa la autenticación del usuario
         */
        private void procesarAutenticacion() throws IOException {
            try {
                // Recibir credenciales
                Credenciales credenciales = (Credenciales) entrada.readObject();
                nombreUsuario = credenciales.getUsuario();
                
                // Validar credenciales (implementar lógica real de autenticación)
                boolean autenticado = validarCredenciales(credenciales);
                
                // Enviar respuesta
                salida.writeObject(new Respuesta("AUTH", autenticado));
                
                if (autenticado) {
                    System.out.println("Usuario autenticado: " + nombreUsuario);
                } else {
                    desconectar();
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Error al recibir credenciales: " + e.getMessage());
                desconectar();
            }
        }
        
        /**
         * Valida las credenciales del usuario
         */
        private boolean validarCredenciales(Credenciales credenciales) {
            // Implementar lógica real de validación con la base de datos
            // Por ahora, autenticamos a cualquier usuario para simplificar
            return true;
        }
        
        /**
         * Procesa la solicitud recibida del cliente
         */
        private void procesarSolicitud(Solicitud solicitud) throws IOException {
            String tipo = solicitud.getTipo();
            Object datos = solicitud.getDatos();
            
            try {
                switch (tipo) {
                    case "REGISTRAR_ANIMAL":
                        registrarAnimal((Animal) datos);
                        break;
                    case "ACTUALIZAR_ANIMAL":
                        actualizarAnimal((Animal) datos);
                        break;
                    case "ELIMINAR_ANIMAL":
                        eliminarAnimal((Integer) datos);
                        break;
                    case "OBTENER_ANIMAL":
                        obtenerAnimal((Integer) datos);
                        break;
                    case "LISTAR_ANIMALES":
                        listarAnimales();
                        break;
                    case "REGISTRAR_EVENTO":
                        registrarEvento((Evento) datos);
                        break;
                    case "ACTUALIZAR_EVENTO":
                        actualizarEvento((Evento) datos);
                        break;
                    case "ELIMINAR_EVENTO":
                        eliminarEvento((Integer) datos);
                        break;
                    case "OBTENER_EVENTOS_ANIMAL":
                        obtenerEventosAnimal((Integer) datos);
                        break;
                    case "DESCONECTAR":
                        desconectar();
                        break;
                    default:
                        enviarRespuesta(new Respuesta("ERROR", "Tipo de solicitud no reconocido"));
                }
            } catch (ClassCastException e) {
                enviarRespuesta(new Respuesta("ERROR", "Formato de solicitud incorrecto"));
            } catch (Exception e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al procesar solicitud: " + e.getMessage()));
            }
        }
        
        /**
         * Registra un nuevo animal en la base de datos
         */
        private void registrarAnimal(Animal animal) throws IOException {
            try {
                int id = animalDAO.insertar(animal);
                
                // Si el animal fue comprado, crear automáticamente evento de cuarentena
                if (animal.isEsComprado()) {
                    Evento eventoCuarentena = new Evento(
                        0, // ID temporal
                        id, // ID del animal recién registrado
                        "Cuarentena",
                        animal.getFechaCompra(),
                        "Iniciada",
                        "Cuarentena automática por compra de animal"
                    );
                    eventoDAO.insertar(eventoCuarentena);
                }
                
                animal.setIdAnimal(id);
                enviarRespuesta(new Respuesta("ANIMAL_REGISTRADO", animal));
            } catch (SQLException e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al registrar animal: " + e.getMessage()));
            }
        }
        
        /**
         * Actualiza un animal existente
         */
        private void actualizarAnimal(Animal animal) throws IOException {
            try {
                boolean actualizado = animalDAO.actualizar(animal);
                enviarRespuesta(new Respuesta("ANIMAL_ACTUALIZADO", actualizado));
            } catch (SQLException e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al actualizar animal: " + e.getMessage()));
            }
        }
        
        /**
         * Elimina un animal por su ID
         */
        private void eliminarAnimal(Integer idAnimal) throws IOException {
            try {
                boolean eliminado = animalDAO.eliminar(idAnimal);
                enviarRespuesta(new Respuesta("ANIMAL_ELIMINADO", eliminado));
            } catch (SQLException e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al eliminar animal: " + e.getMessage()));
            }
        }
        
        /**
         * Obtiene un animal por su ID
         */
        private void obtenerAnimal(Integer idAnimal) throws IOException {
            try {
                Animal animal = animalDAO.obtenerPorId(idAnimal);
                enviarRespuesta(new Respuesta("ANIMAL", animal));
            } catch (SQLException e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al obtener animal: " + e.getMessage()));
            }
        }
        
        /**
         * Lista todos los animales
         */
        private void listarAnimales() throws IOException {
            try {
                List<Animal> animales = animalDAO.listarTodos();
                enviarRespuesta(new Respuesta("LISTA_ANIMALES", animales));
            } catch (SQLException e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al listar animales: " + e.getMessage()));
            }
        }
        
        /**
         * Registra un nuevo evento
         */
        private void registrarEvento(Evento evento) throws IOException {
            try {
                int id = eventoDAO.insertar(evento);
                evento.setIdEvento(id);
                enviarRespuesta(new Respuesta("EVENTO_REGISTRADO", evento));
            } catch (SQLException e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al registrar evento: " + e.getMessage()));
            }
        }
        
        /**
         * Actualiza un evento existente
         */
        private void actualizarEvento(Evento evento) throws IOException {
            try {
                boolean actualizado = eventoDAO.actualizar(evento);
                enviarRespuesta(new Respuesta("EVENTO_ACTUALIZADO", actualizado));
            } catch (SQLException e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al actualizar evento: " + e.getMessage()));
            }
        }
        
        /**
         * Elimina un evento por su ID
         */
        private void eliminarEvento(Integer idEvento) throws IOException {
            try {
                boolean eliminado = eventoDAO.eliminar(idEvento);
                enviarRespuesta(new Respuesta("EVENTO_ELIMINADO", eliminado));
            } catch (SQLException e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al eliminar evento: " + e.getMessage()));
            }
        }
        
        /**
         * Obtiene los eventos de un animal específico
         */
        private void obtenerEventosAnimal(Integer idAnimal) throws IOException {
            try {
                List<Evento> eventos = eventoDAO.obtenerPorAnimal(idAnimal);
                enviarRespuesta(new Respuesta("EVENTOS_ANIMAL", eventos));
            } catch (SQLException e) {
                enviarRespuesta(new Respuesta("ERROR", "Error al obtener eventos del animal: " + e.getMessage()));
            }
        }
        
        /**
         * Envía una respuesta al cliente
         */
        private void enviarRespuesta(Respuesta respuesta) throws IOException {
            salida.writeObject(respuesta);
            salida.flush();
        }
        
        /**
         * Desconecta al cliente
         */
        private void desconectar() {
            conectado = false;
            clientesConectados.remove(this);
            System.out.println("Cliente desconectado: " + (nombreUsuario != null ? nombreUsuario : "desconocido"));
            
            try {
                if (salida != null) salida.close();
                if (entrada != null) entrada.close();
                if (socket != null && !socket.isClosed()) socket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}

//version full del server
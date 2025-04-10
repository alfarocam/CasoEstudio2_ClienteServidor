package com.mycompany.casoestudio2.server;

import com.mycompany.casoestudio2.farm.Animal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {
    public List<Animal> obtenerAnimal() {
        List<Animal> animal = new ArrayList<>();
        String query = "SELECT id_animal, identificacion, raza, sexo, nombre, color_pelaje FROM animal";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                animal.add(new Animal (
                    rs.getInt("id_animal"),
                    rs.getString("identificacion"),
                    rs.getString("raza"),
                    rs.getString("sexo"),
                    rs.getString("nombre"),
                    rs.getString("color_pelaje")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animal;
    }
    
    public boolean insertarAnimal(Animal animal) {
        String query = "INSERT INTO animal (id_animal, identificacion, raza, sexo, nombre, color_pelaje) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, animal.getIdAnimal());
            pstmt.setString(2, animal.getIdentificacion());
            pstmt.setString(3, animal.getRaza());
            pstmt.setString(4, animal.getSexo());
            pstmt.setString(5, animal.getNombre());
            pstmt.setString(5, animal.getColorPelaje());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizarAnimal(Animal animal) {
        String query = "UPDATE animal SET identificacion = ?, raza = ?, sexo = ?, nombre = ?, color_pelaje = ? WHERE id_persona = ?";
        try (Connection conn = Conexion.getConnection();
               PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, animal.getIdAnimal());
            pstmt.setString(2, animal.getIdentificacion());
            pstmt.setString(3, animal.getRaza());
            pstmt.setString(4, animal.getSexo());
            pstmt.setString(5, animal.getNombre());
            pstmt.setString(5, animal.getColorPelaje());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace ();
            return false;
        }
    }
    
    public boolean eliminarAnimal (int id_animal) {
        String query = "DELETE FROM animal WHERE id_animal = 1";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id_animal);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
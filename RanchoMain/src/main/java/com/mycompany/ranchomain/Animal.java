/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ranchomain;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author camil
 */

/**
 * Clase que representa un animal del rancho
 */
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idAnimal;
    private String identificacion;
    private String raza;
    private String sexo;
    private LocalDate fechaNacimiento;
    private double pesoNacimiento;
    private String nombre;
    private String colorPelaje;
    private String notas;
    private boolean esComprado;
    private LocalDate fechaCompra;
    private String hatoOrigen;
    private String fotografia;

    // Constructor por defecto
    public Animal() {
    }

    // Constructor completo
    public Animal(int idAnimal, String identificacion, String raza, String sexo,
            LocalDate fechaNacimiento, double pesoNacimiento, String nombre,
            String colorPelaje, String notas, boolean esComprado,
            LocalDate fechaCompra, String hatoOrigen, String fotografia) {
        this.idAnimal = idAnimal;
        this.identificacion = identificacion;
        this.raza = raza;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.pesoNacimiento = pesoNacimiento;
        this.nombre = nombre;
        this.colorPelaje = colorPelaje;
        this.notas = notas;
        this.esComprado = esComprado;
        this.fechaCompra = fechaCompra;
        this.hatoOrigen = hatoOrigen;
        this.fotografia = fotografia;
    }

    // Getters y setters
    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getPesoNacimiento() {
        return pesoNacimiento;
    }

    public void setPesoNacimiento(double pesoNacimiento) {
        this.pesoNacimiento = pesoNacimiento;
    }
}
//prueba de commit
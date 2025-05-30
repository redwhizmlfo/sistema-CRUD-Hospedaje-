/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Jesus
 */
public class vHabitacion {
    
    // Atributos
    private String nombre;
    private String numero;
    private double precio;
    private String piso;
    private String descripcion;
    private String estado;

    // Constructor
    public vHabitacion(String nombre, String numero, double precio, String piso, String descripcion, String estado) {
        this.nombre = nombre;
        this.numero = numero;
        this.precio = precio;
        this.piso = piso;
        this.descripcion = descripcion;
        this.estado = estado != null ? estado : "Disponible, Ocupado, Mantenimiento"; // Default to "Disponible" if null
    }
    
    public vHabitacion() {
        
    // Inicializa los atributos como vacíos o nulos
        this.nombre = "";
        this.numero = "";
        this.precio = 0;
        this.piso = "";
        this.descripcion = "";
        this.estado = "";
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
     public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getEstado() {
        return estado;
    }   
}

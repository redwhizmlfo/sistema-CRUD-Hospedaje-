/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Jesus
 */
public class vReserva {
   
    private int idCliente;
    private int idHabitacion;
    private int cantPersonas;
    private String fechaEntrada;
    private String fechaSalida;
    private String estado;
    
    // Constructor con parámetros
    public vReserva(int idCliente,int idHabitacion,int cantPersonas ,String fechaEntrada, String fechaSalida, String estado) {    
        this.idCliente = idCliente;
        this.idHabitacion = idHabitacion;
        this.cantPersonas = cantPersonas;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
    }
    
    // Constructor sin parámetros
    public vReserva() {
        
        // Inicializa los atributos con valores por defecto
        this.fechaEntrada = null;
        this.fechaSalida = null;
        this.cantPersonas = 0;
        this.idCliente = 0;
        this.idHabitacion = 0;
        this.estado = "";
    }

    // Getter y Setter para idCliente
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    // Getter y Setter para idHabitacion
    public int getIdHabitacion() {
        return idHabitacion;
    }
    
    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }
    
     // Getter y Setter para cantPersonas
    public int getCantPersonas() {
        return cantPersonas;
    }
    
    public void setCantPersonas(int cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    // Getter y Setter para fechaEntrada
    public String getFechaEntrada() {
        return fechaEntrada;
    }
    
    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }
    
    // Getter y Setter para fechaSalida
    public String getFechaSalida() {
        return fechaSalida;
    }
    
    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    // Getter y Setter para estado
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
   


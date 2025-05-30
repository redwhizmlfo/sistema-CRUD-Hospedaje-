/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Jesus
 */
public class vClientes {
    private String nombre;
    private String apellido;
    private String DNI;
    private String telefono;
    private String direccion;
    
    // Constructor con argumentos
    public vClientes(String nombre,String apellido,String DNI,String telefono,String direccion){
        
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
     // Constructor sin argumentos
    public vClientes() {
        
    // Inicializa los atributos como vacíos o nulos
        this.nombre = "";
        this.apellido = "";
        this.DNI = "";
        this.telefono = "";
        this.direccion = "";
    }
    
     // Getter y Setter para Nombre
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
     // Getter y Setter para Apellido
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // Getter y Setter para DNI
    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    // Getter y Setter para telefono
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Getter y Setter para direccion
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCantPersonas(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

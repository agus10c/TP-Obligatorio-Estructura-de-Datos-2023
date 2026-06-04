/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author acace
 */
public class Cliente {

    private IDCliente ID;
    private String nombre;
    private String apellido;
    private long telefono;
    private String email;

    public Cliente(String td, int nd, String n, String a, long t, String e) {
        this.ID = new IDCliente(td, nd);
        this.nombre = n;
        this.apellido = a;
        this.telefono = t;
        this.email = e;
    }

    public IDCliente getId() {
        return this.ID;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public long getTelefono() {
        return this.telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public void setApellido(String ap) {
        this.apellido = ap;
    }

    public void setTelefono(long tel) {
        this.telefono = tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return ID.toString() + " / " + this.nombre + " " + this.apellido
                + " / " + "Num de Telefono: " + this.telefono + " / Email: " + this.email;
    }
}

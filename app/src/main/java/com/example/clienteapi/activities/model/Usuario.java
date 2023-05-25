package com.example.clienteapi.activities.model;



import java.io.Serializable;
import java.util.Objects;


public class Usuario implements Serializable {
    private int idUsuario; //autoincrement
    private String nombre;
    private String apellidos;
    private int idOficio;

    public Usuario(String nombre, String apellidos, int idOficio) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.idOficio = idOficio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getIdOficio() {
        return idOficio;
    }
}

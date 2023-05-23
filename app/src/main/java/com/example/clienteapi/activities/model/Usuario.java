package com.example.clienteapi.activities.model;



import java.io.Serializable;
import java.util.Objects;


public class Usuario implements Serializable {
    private int idUsuario; //autoincrement
    private String nombre;
    private String apellidos;
    private int idOficio;

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

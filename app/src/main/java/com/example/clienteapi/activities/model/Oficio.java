package com.example.clienteapi.activities.model;


import java.io.Serializable;
import java.util.Objects;

public class Oficio implements Serializable {
    private int idOficio;
    private String descripcion;
    private String imgUrl;

    public int getIdOficio() {
        return idOficio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}

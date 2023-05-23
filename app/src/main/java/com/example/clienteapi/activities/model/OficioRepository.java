package com.example.clienteapi.activities.model;

import com.example.clienteapi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OficioRepository {
    private List<Oficio> profesiones;
    private static OficioRepository instance;


    public OficioRepository(){
        profesiones = new ArrayList<>();

    }

    public static OficioRepository getInstance(){
        if (instance == null)
            instance = new OficioRepository();
        return instance;

    }


    public  List<Oficio> getAll(){
        return  new ArrayList<>(profesiones);
    }


}

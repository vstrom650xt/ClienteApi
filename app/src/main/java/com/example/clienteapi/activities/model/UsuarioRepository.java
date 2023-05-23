package com.example.clienteapi.activities.model;

import com.example.clienteapi.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UsuarioRepository {
    private List<Usuario> usuarios;
    private static UsuarioRepository instance;
    public UsuarioRepository() {
        usuarios = new ArrayList<>();

    }
    public static UsuarioRepository getInstance() {
        if (instance == null) {
            instance = new UsuarioRepository();
        }
        return instance;
    }
    public Usuario get(int index) { return usuarios.get(index); }
    public int size() { return usuarios.size(); }
    public void remove(Usuario usuario) { usuarios.remove(usuario); }
    public void add(Usuario usuario) { usuarios.add(usuario); }
    public void add(int index, Usuario usuario) { usuarios.add(index,
            usuario); }
    public void sort(Comparator comparator) {
        Collections.sort(usuarios, comparator); }

}

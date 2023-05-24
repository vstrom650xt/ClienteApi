package com.example.clienteapi.activities.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clienteapi.R;
import com.example.clienteapi.activities.model.Oficio;
import com.example.clienteapi.activities.model.OficioRepository;
import com.example.clienteapi.activities.model.Usuario;
import com.example.clienteapi.activities.model.UsuarioRepository;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private OficioRepository listaoficios;
    private UsuarioRepository listaUsuario;
    private  LayoutInflater inflater;

    public Adaptador(Context context) {
        listaUsuario = UsuarioRepository.getInstance();
        listaoficios = OficioRepository.getInstance();
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.simple_element,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Usuario usuario = listaUsuario.get(position);
        System.out.println("eee");
        holder.nombre.setText(usuario.getApellidos()+" "+ usuario.getNombre());
        //  Oficio oficio = listaoficios.getInstance().getProfessionByImg(oficio.getImgUrl());
    //    Oficio oficio = listaoficios.getAll().get(position);
    //    holder.profesion.setText(oficio.getIdOficio());
     //   holder.imagen.setImageResource(oficio.getImgUrl());



    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre;
        private TextView profesion;
        private ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.textViewNombre);
            this.profesion = itemView.findViewById(R.id.textViewOficio);
            imagen = itemView.findViewById(R.id.imageView);
        }
    }
}

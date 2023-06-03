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
import com.example.clienteapi.activities.model.Usuario;
import com.example.clienteapi.base.ImageDownloader;
import com.example.clienteapi.base.Parameters;

import java.util.ArrayList;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private List<Oficio> listaoficios;
    private List<Usuario> listaUsuario;
    private  LayoutInflater inflater;
    private  static View.OnClickListener rcvlistener;
//    private OnItemClickListener listener;

    public Adaptador(Context context) {
        listaUsuario = new ArrayList<>();
        listaoficios = new ArrayList<>();
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.simple_element,parent,false);
        view.setOnClickListener(rcvlistener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //relacionar al usuario con el oficio por id y despues con este id sacar la img
        Usuario usuario = listaUsuario.get(position);
        holder.nombre.setText(usuario.getApellidos()+" "+ usuario.getNombre());

         Oficio oficio = listaoficios.get(listaUsuario.get(position).getIdOficio()-1);
         holder.profesion.setText(oficio.getDescripcion());

        ImageDownloader.downloadImage(inflater.getContext(), Parameters.URL_OPTIONS + oficio.getImgUrl(),holder.imagen,R.mipmap.ic_launcher);



    }
    public void setData(List<Usuario> usuarioList, List<Oficio> listaoficios){
        this.listaUsuario = usuarioList;
        this.listaoficios = listaoficios;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        rcvlistener = onClickListener;
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

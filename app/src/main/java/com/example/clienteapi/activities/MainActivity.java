package com.example.clienteapi.activities;

import android.os.Bundle;
import android.provider.DocumentsContract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clienteapi.API.Connector;
import com.example.clienteapi.R;
import com.example.clienteapi.activities.logic.Adaptador;
import com.example.clienteapi.activities.model.Usuario;
import com.example.clienteapi.activities.model.UsuarioRepository;
import com.example.clienteapi.base.BaseActivity;
import com.example.clienteapi.base.CallInterface;

import java.util.List;

//el baseactivity es necesario para hacer elececute call
public class MainActivity extends BaseActivity implements CallInterface {
private RecyclerView rcv;
private Usuario usuario;
private UsuarioRepository usuarioRepository;

private List<Usuario> usuarios;
    public  void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById( R.id.rcv);
        showProgress();
        executeCall(this);

    }

    ///

    @Override
    public void doInBackground() {

        usuarios = Connector.getConector().getAsList(Usuario.class,"usuarios/");



    }

    @Override
    public void doInUI() {
        hideProgress();
        for (int i = 0; i <usuarios.size() ; i++) {
            System.out.println(usuarios.get(i));

        }

    }
}

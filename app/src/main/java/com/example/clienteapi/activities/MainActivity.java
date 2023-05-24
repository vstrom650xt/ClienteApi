package com.example.clienteapi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clienteapi.API.Connector;
import com.example.clienteapi.R;
import com.example.clienteapi.activities.logic.Adaptador;
import com.example.clienteapi.activities.model.Oficio;
import com.example.clienteapi.activities.model.Usuario;
import com.example.clienteapi.base.BaseActivity;
import com.example.clienteapi.base.CallInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

//el baseactivity es necesario para hacer elececute call
public class MainActivity extends BaseActivity implements CallInterface {
private RecyclerView rcv;

private List <Oficio> oficios;

private List<Usuario> usuarios;

private FloatingActionButton addbtn ;
    public  void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById( R.id.rcv);
        showProgress();
        executeCall(this);
        ActivityResultLauncher <Intent> someactivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result ->{

        });

        addbtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormularyActivity.class);
            intent.putExtra("oficios",(ArrayList)oficios);
            someactivity.launch(intent);


        });


    }

    ///

    @Override
    public void doInBackground() {

        usuarios = Connector.getConector().getAsList(Usuario.class,"usuarios/");
        oficios = Connector.getConector().getAsList(Oficio.class,"oficios/");
        System.out.println("123132");

    }

    @Override
    public void doInUI() {
        hideProgress();
        Adaptador adaptador = new Adaptador(this);
        rcv.setAdapter(adaptador);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        adaptador.setData(usuarios,oficios);

    }

}

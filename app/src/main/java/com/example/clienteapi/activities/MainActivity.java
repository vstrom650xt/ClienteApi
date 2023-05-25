package com.example.clienteapi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

//el baseactivity es necesario para hacer elececute call
public class MainActivity extends BaseActivity implements CallInterface {
private RecyclerView rcv;

private List <Oficio> oficios;

private List<Usuario> usuarios;

private FloatingActionButton addbtn ;
private Usuario usuario;

private Adaptador adaptadorRecy;
    public  void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById( R.id.rcv);
        addbtn = findViewById(R.id.floatingActionButton);
        showProgress();
        executeCall(this);

         adaptadorRecy = new Adaptador(this);
        rcv.setAdapter(adaptadorRecy);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        ActivityResultLauncher <Intent> someactivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result ->{

            if (result.getResultCode() == Activity.RESULT_OK){
                Intent data = result.getData();
                if (data != null){
                    Usuario usuario;
                    usuario = (Usuario) data.getExtras().getSerializable("usuario");
                    usuarios.add(usuario);
                    adaptadorRecy.notifyDataSetChanged();
                }


            }

        });

        addbtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormularyActivity.class);
            intent.putExtra("oficios",(ArrayList)oficios);
            someactivity.launch(intent);


        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;   //para ordenar
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                //remove usuario
                executeCall(new CallInterface() {
                    @Override
                    public void doInBackground() {
                        usuario  = usuarios.get(position);
                        int i = usuario.getIdUsuario();
                        usuarios.remove(position);
                        usuario = Connector.getConector().delete(Usuario.class,"usuarios/"+i);
                    }

                    @Override
                    public void doInUI() {
                        adaptadorRecy.notifyItemRemoved(position);
                        adaptadorRecy.notifyDataSetChanged();
                    }
                });

                Snackbar.make(rcv, "deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //updateUser
                        usuario  = usuarios.get(position);
                        executeCall(new CallInterface() {
                            @Override
                            public void doInBackground() {
                                usuario = usuarios.get(position);


                            }

                            @Override
                            public void doInUI() {
                                adaptadorRecy.notifyItemInserted(position);
                                adaptadorRecy.notifyDataSetChanged();



                            }
                        });
                    }
                }).show();
            }
        });


        itemTouchHelper.attachToRecyclerView(rcv);
        }

    ///

    @Override
    public void doInBackground() {
        usuarios = Connector.getConector().getAsList(Usuario.class,"usuarios/");
        oficios = Connector.getConector().getAsList(Oficio.class,"oficios/");
    }

    @Override
    public void doInUI() {
        hideProgress();
        adaptadorRecy.setData(usuarios,oficios);
        adaptadorRecy.notifyDataSetChanged();
    }
}

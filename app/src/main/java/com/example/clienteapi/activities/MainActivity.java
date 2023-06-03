package com.example.clienteapi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

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
public class MainActivity extends BaseActivity implements CallInterface, View.OnClickListener {
    private RecyclerView rcv;

    private List<Oficio> oficios;

    private List<Usuario> usuarios;

    private FloatingActionButton addbtn;
    private Usuario usuario;
    private Usuario usuarioupdate;

    ActivityResultLauncher<Intent> activityUpdate;

    private Adaptador adaptadorRecy;


    private PopupWindow popupWindow;



    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById(R.id.rcv);
        addbtn = findViewById(R.id.floatingActionButton);

        showProgress();
        executeCall(this);

        adaptadorRecy = new Adaptador(this);
        rcv.setAdapter(adaptadorRecy);
        adaptadorRecy.setOnClickListener(this);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        ActivityResultLauncher<Intent> someactivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Usuario usuario;
                    usuario = (Usuario) data.getExtras().getSerializable("usuario");
                    usuarios.add(usuario);
                    adaptadorRecy.notifyDataSetChanged();
                }


            }

        });

        activityUpdate = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        });

        addbtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormularyActivity.class);
            intent.putExtra("oficios", (ArrayList) oficios);
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

                Usuario  usuarioauxiliar  = usuarios.get(position);
                                //remove usuario
                executeCall(new CallInterface() {
                    @Override
                    public void doInBackground() {
                        usuario = usuarios.get(position);
                        int i = usuario.getIdUsuario();
                        usuarios.remove(position);
                        usuario = Connector.getConector().delete(Usuario.class, "usuarios/" + i);
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
                       // on click del undo auxilar persona
                        usuarios.add(position,usuarioauxiliar);
                        executeCall(new CallInterface() {
                            @Override
                            public void doInBackground() {
                                usuario = usuarios.get(position);
                               usuario= Connector.getConector().post(Usuario.class, usuarioauxiliar, "usuarios/");
                         //       usuarios.add(position,usuario);
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


        /////


    }

    ///


    @Override
    public void doInBackground() {
        usuarios = Connector.getConector().getAsList(Usuario.class, "usuarios/");
        oficios = Connector.getConector().getAsList(Oficio.class, "oficios/");
    }

    @Override
    public void doInUI() {
        hideProgress();
        adaptadorRecy.setData(usuarios, oficios);
        adaptadorRecy.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        getChildByPosition(rcv.getChildAdapterPosition(v));
        usuario = usuarios.get(rcv.getChildAdapterPosition(v));

        int pos = rcv.getChildAdapterPosition(v);
        System.out.println(pos);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_layout, null);


        Button acceptButton = popupView.findViewById(R.id.popacept);
        Button cancelButton = popupView.findViewById(R.id.popcancel);
        EditText textonombre = popupView.findViewById(R.id.editnombre); // Corregido aquí
        EditText textoficio = popupView.findViewById(R.id.editoficio); // Corregido aquí

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//poner
                System.out.println(usuario.toString());
                String nombre = textonombre.getText().toString();
                String oficio = textoficio.getText().toString();
                usuarioupdate = new Usuario(usuario.getIdUsuario(),nombre, oficio, usuario.getIdOficio());
                System.out.println(usuarioupdate.toString());
                executeCall(new CallInterface() {
                    @Override
                    public void doInBackground() {
                        usuario = Connector.getConector().put(Usuario.class, usuarioupdate, "usuarios/");

                    }

                    @Override
                    public void doInUI() {
                        usuarios.set(pos, usuario);
                        adaptadorRecy.notifyItemChanged(pos);
                        adaptadorRecy.notifyDataSetChanged();
                    }
                });
                adaptadorRecy.notifyDataSetChanged();

                popupWindow.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // Acciones a realizar al cerrar la ventana emergente
            }
        });
    }


    private View getChildByPosition(int position) {
        RecyclerView.ViewHolder viewHolder = rcv.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {

            return viewHolder.itemView;
        } else {
            return null;
        }
    }


    private int getChildByPosition2(int position) {
        RecyclerView.ViewHolder viewHolder = rcv.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {

            return viewHolder.getPosition();
        } else {
            return -1;
        }
    }

//    private void showPopup() {
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View popupView = inflater.inflate(R.layout.popup_layout, null);
//
//        // Configurar el contenido y el diseño de la ventana emergente
//        // Aquí puedes agregar elementos y definir su comportamiento
//
//        int width = 200;
//        int height =200;
//        boolean focusable = true; // Permite interactuar con la ventana emergente mientras está activa
//
//        popupWindow = new PopupWindow(popupView, width, height, focusable);
//
//        // Mostrar la ventana emergente en una posición específica
//        popupWindow.showAtLocation(null, Gravity.CENTER, 0, 0);
//
//        // Configurar el comportamiento al cerrar la ventana emergente
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                // Acciones a realizar al cerrar la ventana emergente
//            }
//        });
//    }
}

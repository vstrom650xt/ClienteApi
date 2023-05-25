package com.example.clienteapi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clienteapi.API.Connector;
import com.example.clienteapi.R;
import com.example.clienteapi.activities.model.Oficio;
import com.example.clienteapi.activities.model.Usuario;
import com.example.clienteapi.base.BaseActivity;
import com.example.clienteapi.base.CallInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class FormularyActivity extends BaseActivity {

    private Usuario usuario;
    private ArrayList<Oficio> oficios;
    private TextInputEditText tietnombre;
    private TextInputEditText tietapellidos;
    private Spinner spinner;
    private Button bAceptar;
    private Button bCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        tietnombre = findViewById(R.id.tietNombre);
        tietapellidos = findViewById(R.id.tietApellidos);
        spinner = findViewById(R.id.spinner);
        bAceptar = findViewById(R.id.buttonAceptar);
        bCancelar = findViewById(R.id.buttonCancelar);
        Bundle bundle = getIntent().getExtras();


        oficios =(ArrayList<Oficio>) bundle.getSerializable("oficios");
//Cargamos el spinner con las profesiones
        ArrayAdapter<Oficio> myAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                oficios);

             // devuelve listado con todas las profesiones
        spinner.setAdapter(myAdapter);
//gestionamos el botón cancelar comunicando que el resultado se canceló
        bCancelar.setOnClickListener(v -> {
            Intent i = new Intent();
            setResult(RESULT_CANCELED,i);
            finish();
        });
//gestionamos el botón aceptar comunicando la intención con la  información a pasar al layout que llamó
        bAceptar.setOnClickListener(v -> {

            String nombre = tietnombre.getText().toString();
            String apellidos = tietapellidos.getText().toString();
            Oficio oficio = (Oficio) spinner.getSelectedItem();
            usuario = new Usuario(nombre,apellidos,oficio.getIdOficio());
           executeCall(new CallInterface() {
               @Override
               public void doInBackground() {
                   usuario = Connector.getConector().post(Usuario.class,usuario,"usuarios/");

               }

               @Override
               public void doInUI() {

                  Intent  i = new Intent(FormularyActivity.this,MainActivity.class);
                   i.putExtra("usuario", usuario);
                   setResult(RESULT_OK,i);
                   finish();


               }
           });
        });
    }
}

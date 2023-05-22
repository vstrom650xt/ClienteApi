package com.example.clienteapi.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clienteapi.R;
import com.example.clienteapi.base.BaseActivity;
import com.example.clienteapi.base.CallInterface;
//el baseactivity es necesario para hacer elececute call
public class MainActivity extends BaseActivity implements CallInterface {
private RecyclerView rcv;
    public  void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById( R.id.rcv);


    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void doInUI() {

    }
}

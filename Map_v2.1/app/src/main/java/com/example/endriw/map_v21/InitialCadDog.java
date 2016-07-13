package com.example.endriw.map_v21;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class InitialCadDog extends AppCompatActivity {

    public TextView txt_nenhum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_cadog);
    }


    public void Aparece(View view) {

        txt_nenhum = (TextView) findViewById(R.id.nenhumdog);
        txt_nenhum.setVisibility(view.VISIBLE);
    }
}
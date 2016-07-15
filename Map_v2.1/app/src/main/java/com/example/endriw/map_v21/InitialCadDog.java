package com.example.endriw.map_v21;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class InitialCadDog extends AppCompatActivity {

    public TextView txt_nenhum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_cadog);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.caddog_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent intent  = new Intent(this, CadDog.class);
        int id = item.getItemId();

        if(id == R.id.action_addog){
          startActivity(intent);
        }
        else if(id == R.id.action_help){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cadastre um cão !" +
                    "Utilize esta tela para cadastrar, alterar ou remover um cachorro perdido !");
            builder.create();
            builder.show();
        }

        return true;
    }

    public void Aparece(View view) {

        txt_nenhum = (TextView) findViewById(R.id.nenhumdog);
        txt_nenhum.setVisibility(view.VISIBLE);
    }
}
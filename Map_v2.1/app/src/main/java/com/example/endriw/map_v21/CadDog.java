package com.example.endriw.map_v21;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;


public class CadDog extends AppCompatActivity {

    public Toolbar toolbar;
    private String array_spinner[];
    private PopupWindow pup;

    private String[] data = {"Data"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_dog);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.caddog_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cadastre um cão !!" +
                    "aqui você poderá adicionar uma foto do seu cão perdido, data o nome (ou apelido) e descrever " +
                    "as caracterisitcas dele, ou até mesmo oferecer recompensas.");
            builder.create();
            builder.show();
        }

        return true;
    }


    public void CallCalendar(View view) {
        DialogFragment dialogfragment = new DogCalendar();

        dialogfragment.show(getFragmentManager(), "Dog Calendar !!");
    }


    public void ClickSalvar(View view) {
        Toast.makeText(getApplicationContext(), "oi", Toast.LENGTH_SHORT).show();
    }

    public void ClickCancelar(View view) {

        this.finish();
    }
}

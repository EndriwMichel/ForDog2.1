package com.example.endriw.map_v21;


import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.firebase.client.Firebase;


public class CadDog extends AppCompatActivity {

    public Toolbar toolbar;
    private String array_spinner[];
    private PopupWindow pup;
    private Firebase mRef;

    private String[] data = {"Data"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_dog);
        mRef = new Firebase("https://fordog.firebaseio.com/");

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            dialogfragment.show(getFragmentManager(), "Dog Calendar !!");
        }
    }


    public void ClickSalvar(View view) {

        //ImageButton  elementoFoto = (ImageButton) findViewById(R.id.dogFoto);
        TextView elementoData = (TextView) findViewById(R.id.dogDate);
        TextView elementoDesc = (TextView) findViewById(R.id.dogDesc);
        TextView elementoNome = (TextView) findViewById(R.id.dogNome);

        String stDogNome = elementoNome.getText().toString();
        String stDogDesc = elementoDesc.getText().toString();
        String stDogData = elementoData.getText().toString();
        String stHash = stDogData + stDogDesc + stDogData;
        int key = stHash.hashCode();
        //        String ftDogfoto = elementoData.getText().toString();

        gravaFirebase(key, stDogNome ,stDogDesc, stDogData, "", "");
    }


    public void ClickCancelar(View view) {
        this.finish();
    }

    public void gravaFirebase(int key, String dogNome, String dogDesc, String dogData, String latitude, String longitude) {
        //mRef.child(key).setValue(valor);

        if(latitude == "")
            //pega a location com o helper do endriw
        if(longitude == "")
            //pega a location com o helper do endriw

        mRef.child( String.valueOf( key ) ).child("latitude").setValue("13");
        mRef.child( String.valueOf( key ) ).child("longitude").setValue("13");
        mRef.child( String.valueOf( key ) ).child("dogNome").setValue(dogNome);
        mRef.child( String.valueOf( key ) ).child("dogDesc").setValue(dogDesc);
        mRef.child( String.valueOf( key ) ).child("dogData").setValue(dogData);
    }
}

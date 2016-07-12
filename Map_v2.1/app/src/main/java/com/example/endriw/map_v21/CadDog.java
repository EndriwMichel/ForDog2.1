package com.example.endriw.map_v21;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;


public class CadDog extends AppCompatActivity  {

    public Toolbar toolbar;
    private String array_spinner[];
    private PopupWindow pup;

    private String[] data = {"Data"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_dog);


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

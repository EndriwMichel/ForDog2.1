package com.example.endriw.map_v21;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;

public class LostDog extends FragmentActivity {

    private Spinner sp_cor;
    private Spinner sp_porte;
    private EditText stDesc;
    private ImageView im;

    private Bitmap myBitmap;
    private Double latitude;
    private Double longitude;

    private String base64Image;
    private byte[] bytes;


    private String day;
    private String month;
    private String year;
    private String date;
    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lost_dog);

        sp_cor = (Spinner) findViewById(R.id.dogCor);
        sp_porte = (Spinner) findViewById(R.id.dogPorte);
        stDesc = (EditText) findViewById(R.id.dogDesc);
        im = (ImageView) findViewById(R.id.dogFoto);

        Bundle extras = getIntent().getExtras();

//        myBitmap = (Bitmap) getIntent().getParcelableExtra("bitmap");

       /* byte[] bytearray = getIntent().getByteArrayExtra("bitmap");
        myBitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
       */
        String namePath = extras.getString("bitmap");
        File imgFile = new File(namePath);
        myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        im.setImageBitmap(myBitmap);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 200, 200, true);
        resized.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        bytes = baos.toByteArray();
        base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }


        sp_cor = (Spinner) findViewById(R.id.dogCor);
        sp_porte = (Spinner) findViewById(R.id.dogPorte);

        ArrayAdapter<String> dogCor_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("Selecione uma cor");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        //------------------------------------------------------------------------------------------------------------
        ArrayAdapter<String> dogPorte_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("Selecione o porte");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        //adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);

        dogCor_adapter.add("Selecione a cor");
        dogCor_adapter.add("Preto");
        dogCor_adapter.add("Branco");
        dogCor_adapter.add("Marrom");
        dogCor_adapter.add("Preto/Branco");
        dogCor_adapter.add("Preto/Marrom");
        dogCor_adapter.add("Marrom/Branco");
        dogCor_adapter.add("Selecione uma cor");

        dogPorte_adapter.add("Selecione o porte");
        dogPorte_adapter.add("Grande");
        dogPorte_adapter.add("Médio");
        dogPorte_adapter.add("Pequeno");
        dogPorte_adapter.add("Selecione o porte");

        sp_cor.setAdapter(dogCor_adapter);
        sp_cor.setSelection(dogCor_adapter.getCount()); //display hint

        sp_porte.setAdapter(dogPorte_adapter);
        sp_porte.setSelection(dogPorte_adapter.getCount());
    }

    public void ClickSalvar(View view) {

         if(sp_cor.getSelectedItem().equals("Selecione uma cor")){
            Toast.makeText(this, "Por favor selecione uma cor !",
                    Toast.LENGTH_SHORT).show();
        }else if(sp_porte.getSelectedItem().equals("Selecione o porte")){
            Toast.makeText(this, "Por favor selecione um porte !",
                    Toast.LENGTH_SHORT).show();
        }
        else{
             Bundle extras = getIntent().getExtras();

             latitude = extras.getDouble("latitude");
             longitude = extras.getDouble("longitude");

             day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
             month = String.valueOf(c.get(Calendar.MONTH)+1);
             year = String.valueOf(c.get(Calendar.YEAR));
             date = day+" / "+month+" / "+year;
             String elementoDesc = stDesc.getText().toString();

             String stHash = String.valueOf(date)+ String.valueOf(latitude) + String.valueOf(longitude);
             int key = stHash.hashCode();

             String email = MapsActivity.accountName.replace(".", "@");
            dogFirebase fireData = new dogFirebase();
            fireData.gravaLost(email, key, elementoDesc, date, String.valueOf(latitude), String.valueOf(longitude), base64Image, "Cel", sp_porte.getSelectedItem().toString(), sp_cor.getSelectedItem().toString());

            Toast.makeText(this, "Cadastro efetuado",
                    Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    public void ClickCancelar(View view) {
        this.finish();
    }

}
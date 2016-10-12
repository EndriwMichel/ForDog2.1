package com.example.endriw.map_v21;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;


public class CadDog extends AppCompatActivity {

    public Toolbar toolbar;
    private String dogCor[] = new String[]{"Preto", "Branco", "Marrom", "Preto/Branco", "Marrom/Branco", "Marrom/Preto", "Outra cor"};
    private String dogPorte[] = new String[]{"Grande", "Médio", "Pequeno"};

    ArrayAdapter<String> dogCor_adapter;
    ArrayAdapter<String> dogPorte_adapter;

    TextView elementoData;
    TextView elementoDesc;
    TextView elementoNome;
    private Spinner sp_cor;
    private Spinner sp_porte;

    private ImageView dogFoto;
    private String base64Image;
    private byte[] bytes;

    private String latitude = String.valueOf(MapsActivity.latitude);
    private String longitude= String.valueOf(MapsActivity.longitude);

    private BitmapDrawable bitmapDrawable;
    //private String accountName;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    private static final int REQUEST_CODE_EMAIL = 1;

    private String[] data = {"Data"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cad_dog);

        dogFoto = (ImageView) findViewById(R.id.dogFoto);
        sp_cor = (Spinner) findViewById(R.id.dogCor);
        sp_porte = (Spinner) findViewById(R.id.dogPorte);
        elementoData = (TextView) findViewById(R.id.dogDate);
        elementoNome = (TextView) findViewById(R.id.dogNome);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dog, menu);
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

        //valores
        String stDogNome = elementoNome.getText().toString();
        String stDogData = elementoData.getText().toString();
        String stHash = stDogData + stDogNome + stDogData;
        int key = stHash.hashCode();

        if(bitmapDrawable == null){
            Toast.makeText(this, "Por favor adicione uma imagem !",
                    Toast.LENGTH_SHORT).show();
        }else if(stDogNome.equals(null) || stDogNome.equals("".trim())){
            Toast.makeText(this, "Por favor cadastre um nome !",
                    Toast.LENGTH_SHORT).show();
        }else if(stDogData.equals(null) || stDogData.equals("".trim())){
            Toast.makeText(this, "Por favor cadastre uma data !",
                    Toast.LENGTH_SHORT).show();
        }else if(sp_cor.getSelectedItem().equals("Selecione uma cor")){
            Toast.makeText(this, "Por favor selecione uma cor !",
                    Toast.LENGTH_SHORT).show();
        }else if(sp_porte.getSelectedItem().equals("Selecione o porte")){
            Toast.makeText(this, "Por favor selecione um porte !",
                    Toast.LENGTH_SHORT).show();
        }

        else{


            String email = MapsActivity.accountName.replace(".", "@");
            dogFirebase fireData = new dogFirebase();
            fireData.gravaOwn(email, key, stDogNome, stDogData, latitude, longitude, base64Image, "Cel", sp_porte.getSelectedItem().toString(), sp_cor.getSelectedItem().toString());

            Toast.makeText(this, "Cadastro efetuado",
                    Toast.LENGTH_LONG).show();
            this.finish();
        }
    }


    public void ClickCancelar(View view) {
        this.finish();
    }

    public void GetImage(View view) {

        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                try {
                    // When an Image is picked
                    if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                            && null != data) {
                        // Get the Image from data

                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        // Get the cursor
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgDecodableString = cursor.getString(columnIndex);
                        cursor.close();
                        dogFoto = (ImageView) findViewById(R.id.dogFoto);
                      //  ImageView ivew;
                       // ivew = (ImageView)findViewById(R.id.imageView);
                        // Set the Image in ImageView after decoding the String
                    //    ivew.setImageBitmap(BitmapFactory
                      //          .decodeFile(imgDecodableString));
                        bitmapDrawable = new BitmapDrawable(getResources(), imgDecodableString);

                       // dogFoto.setBackground(bitmapDrawable);
                        dogFoto.setImageDrawable(bitmapDrawable);

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString, options);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                        resized.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        bytes = baos.toByteArray();
                        base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                        baos.close();

                    } else {
                        Toast.makeText(this, "You haven't picked Image",
                                Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }
    }
}
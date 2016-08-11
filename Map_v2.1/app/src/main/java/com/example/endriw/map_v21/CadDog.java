package com.example.endriw.map_v21;


import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.firebase.client.Firebase;


public class CadDog extends AppCompatActivity {

    public Toolbar toolbar;
    private String array_spinner[];
    private PopupWindow pup;
    private Firebase mRef;
    private ImageButton dogFoto;

    private final static int SELECT_PHOTO = 0;

    private String[] data = {"Data"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_dog);
        dogFoto = (ImageButton)findViewById(R.id.dogFoto);
        mRef = new Firebase("https://naelsorest.firebaseio.com/");

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
        String email = "coloca aqui a funcao que pega o email";
        gravaFirebase("", key, stDogNome, stDogDesc, stDogData, "", "");
    }


    public void ClickCancelar(View view) {
        this.finish();
    }

    public void gravaFirebase(String email, int key, String dogNome, String dogDesc, String dogData, String latitude, String longitude) {
        //mRef.child(key).setValue(valor);

        if (latitude == "")
            //pega a location com o helper do endriw
        if (longitude == "")
            //pega a location com o helper do endriw

        email = (email != "") ? email : "email";

        //int lostDog = 1;
        //if( lostDog == 1 ){
            mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogNome").setValue(dogNome);
            mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogDesc").setValue(dogDesc);
            mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogData").setValue(dogData);
            mRef.child(email).child("lostDog").child(String.valueOf(key)).child("latitude").setValue("13");
            mRef.child(email).child("lostDog").child(String.valueOf(key)).child("longitude").setValue("13");
        //} else {
            mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogNome").setValue(dogNome);
            mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogDesc").setValue(dogDesc);
            mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogData").setValue(dogData);
            mRef.child(email).child("ownDog").child(String.valueOf(key)).child("latitude").setValue("15");
            mRef.child(email).child("ownDog").child(String.valueOf(key)).child("longitude").setValue("15");
        //}
    }

    public void GetImage(View view) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.addCategory(i.CATEGORY_OPENABLE);
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            dogFoto.setImageBitmap(bitmap);

        }
    }
}
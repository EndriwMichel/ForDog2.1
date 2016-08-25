package com.example.endriw.map_v21;

import android.accounts.AccountManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

import java.io.ByteArrayOutputStream;


public class CadDog extends AppCompatActivity {

    public Toolbar toolbar;
    private String array_spinner[];
    private PopupWindow pup;
    private Firebase mRef;
    private ImageButton dogFoto;
    private String base64Image;
    private byte[] bytes;

    private BitmapDrawable bitmapDrawable;
    //private String accountName;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    private static final int REQUEST_CODE_EMAIL = 1;

    private String[] data = {"Data"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_dog);
        dogFoto = (ImageButton) findViewById(R.id.dogFoto);
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

        ImageButton elementoFoto = (ImageButton) findViewById(R.id.dogFoto);
        TextView elementoData = (TextView) findViewById(R.id.dogDate);
        TextView elementoDesc = (TextView) findViewById(R.id.dogDesc);
        TextView elementoNome = (TextView) findViewById(R.id.dogNome);


        String stDogNome = elementoNome.getText().toString();
        String stDogDesc = elementoDesc.getText().toString();
        String stDogData = elementoData.getText().toString();
        String stHash = stDogData + stDogDesc + stDogData;
        int key = stHash.hashCode();
        //        String ftDogfoto = elementoData.getText().toString();
        String email = MapsActivity.accountName.replace(".", "@");
        gravaFirebase(email, key, stDogNome, stDogDesc, stDogData, "", "", "");

    }


    public void ClickCancelar(View view) {
        this.finish();
    }

    public void gravaFirebase(String email, int key, String dogNome, String dogDesc, String dogData, String latitude, String longitude, String dogFoto) {
        //mRef.child(key).setValue(valor);

        if (latitude == "")
            //pega a location com o helper do endriw
            if (longitude == "")
                //pega a location com o helper do endriw

                email = (email != "") ? email : "email";

        mRef.child("emails").child(String.valueOf(email.hashCode())).child("email").setValue(email);

        //int lostDog = 1;
        //if( lostDog == 1 ){
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogNome").setValue(dogNome);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogDesc").setValue(dogDesc);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogData").setValue(dogData);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("latitude").setValue("13");
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("longitude").setValue("13");
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogFoto").setValue(base64Image);
        //} else {
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogNome").setValue(dogNome);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogDesc").setValue(dogDesc);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogData").setValue(dogData);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("latitude").setValue("15");
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("longitude").setValue("15");
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogFoto").setValue(base64Image);
        //}
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
                        dogFoto = (ImageButton) findViewById(R.id.dogFoto);
                      //  ImageView ivew;
                       // ivew = (ImageView)findViewById(R.id.imageView);
                        // Set the Image in ImageView after decoding the String
                    //    ivew.setImageBitmap(BitmapFactory
                      //          .decodeFile(imgDecodableString));
                        bitmapDrawable = new BitmapDrawable(getResources(), imgDecodableString);
                        dogFoto.setBackground(bitmapDrawable);

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
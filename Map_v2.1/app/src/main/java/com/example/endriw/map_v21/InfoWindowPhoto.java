package com.example.endriw.map_v21;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;


public class InfoWindowPhoto extends AppCompatActivity {

    private TextView tx_accont;
    private TextView tx_tel;
    private ImageView imageView;
    private String photo;
    private Firebase mRef;
    private String userEmail;
    private String dogCel;
    private String dogNick;

    private byte[] imageDog;

    MapsActivity ma = new MapsActivity();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_window_photo);

        tx_accont = (TextView)findViewById(R.id.account);
        tx_tel = (TextView)findViewById(R.id.tel);
        imageView = (ImageView)findViewById(R.id.expand_image);

        Bundle extras = getIntent().getExtras();
        imageDog = extras.getByteArray("DogFoto");
        userEmail = extras.getString("UserEmail");

        Bitmap bmp = BitmapFactory.decodeByteArray(imageDog, 0, imageDog.length);

        FireB();

        imageView.setImageBitmap(bmp);
        tx_accont.setText(userEmail);
        tx_tel.setText(dogCel);
        System.out.println("nome do maluko: "+dogNick);
    }

    private void FireB(){
        mRef = new Firebase("https://dog-603e7.firebaseio.com/");

         mRef.child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {

                 for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                     userDog userDog = dataSnapshot1.getValue(userDog.class);

                     dogCel = userDog.getDogCel();
                     dogNick = userDog.getDogNick();
                 }
             }
             @Override
             public void onCancelled(FirebaseError firebaseError) {

             }
         });
    }

}

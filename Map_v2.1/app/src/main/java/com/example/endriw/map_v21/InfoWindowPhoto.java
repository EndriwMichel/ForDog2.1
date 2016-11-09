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
    String dogNick = null;

    private byte[] imageDog;

    MapsActivity ma = new MapsActivity();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_window_photo);
        setTitle("Informações");

        tx_accont = (TextView)findViewById(R.id.account);
        tx_tel = (TextView)findViewById(R.id.tel);
        imageView = (ImageView)findViewById(R.id.expand_image);

        Bundle extras = getIntent().getExtras();
        imageDog = extras.getByteArray("DogFoto");
        userEmail = extras.getString("UserEmail");

        Bitmap bmp = BitmapFactory.decodeByteArray(imageDog, 0, imageDog.length);

        FireB();

        imageView.setImageBitmap(bmp);

        System.out.println("Nome do usuário: "+dogNick);

    }

    public void FireB() {

        mRef = new Firebase("https://dog-603e7.firebaseio.com/");
        mRef.child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot dataSnapshot : data.getChildren()) {

                    Emails email = dataSnapshot.getValue(Emails.class);
                    String stEmail = email.getEmail();
                    String account_email = MapsActivity.accountName.replace(".", "@");

                    mRef.child(userEmail).child("userDog").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override

                        public void onDataChange(DataSnapshot dataSnapshots) {

                            final Cachorro cachorro = dataSnapshots.getValue(Cachorro.class);
                            //   System.out.println("dados do usuario: "+cachorro.getDogNick()+" / "+cachorro.getDogCel()+" / "+cachorro.getDogNotify());

                            dogNick = cachorro.getDogNick();
                            dogCel = cachorro.getDogCel();
                            tx_accont.setText(dogNick);

                            tx_tel.setText("Tel: "+dogCel);

                            }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}

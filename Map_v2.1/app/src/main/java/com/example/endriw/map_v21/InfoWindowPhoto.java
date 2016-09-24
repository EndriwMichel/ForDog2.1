package com.example.endriw.map_v21;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.w3c.dom.Text;


public class InfoWindowPhoto extends AppCompatActivity {

    private TextView tx_accont;
    private TextView tx_tel;
    private ImageView imageView;
    private String photo;

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

        Bitmap bmp = BitmapFactory.decodeByteArray(imageDog, 0, imageDog.length);

        imageView.setImageBitmap(bmp);
        tx_accont.setText(ma.accountName);
        tx_tel.setText("telefone do maluko");
    }
}

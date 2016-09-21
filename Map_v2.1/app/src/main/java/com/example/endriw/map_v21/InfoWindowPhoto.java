package com.example.endriw.map_v21;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class InfoWindowPhoto extends AppCompatActivity {

    private ImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_window_photo);

        imageView = (ImageView)findViewById(R.id.expand_image);
        System.out.println("Deu certo");
    }
}

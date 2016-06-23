package com.example.endriw.map_v21;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileReader;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Random rand = new Random();

    private GoogleMap mMap;
    private CameraUpdate up;
    private Firebase mRef;
    private String mTexto;

    private EditText text1;
    private EditText text2;
    private Button buton;
    private ImageView iv;


    private ImageButton Cambtn;
    private boolean suces = true;
    private File imageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://fordog.firebaseio.com/");
        text1 = (EditText) findViewById(R.id.editText);
        text2 = (EditText) findViewById(R.id.editText2);
        buton = (Button) findViewById(R.id.button);
        Cambtn = (ImageButton) findViewById(R.id.imageButton);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
   }

    protected void onResume() {
        super.onResume();
        SetUpMapIfNeeded();
    }

    public void ClickButton(View view) {
            mRef = new Firebase("https://fordog.firebaseio.com/dogim");
            mRef.child("latitude").setValue(String.valueOf(text1.getText()));
            mRef.child("longitude").setValue(String.valueOf(text2.getText()));
            LatLng mLoc = new LatLng(Integer.parseInt(String.valueOf(text1.getText())), Integer.parseInt(String.valueOf(text2.getText())));
            mMap.addMarker(new MarkerOptions().position(mLoc).title(String.valueOf(iv)));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc));
            up = CameraUpdateFactory.newLatLngZoom(mLoc, 5);
            mMap.animateCamera(up);
  }

    public void ActionCamera(View view) {
        File direct = new File(Environment.getExternalStorageDirectory()+"/Pictures/findog");
        // File direct2 = new File("/sdcard/");

        if(!direct.exists()) {

            suces = direct.mkdir();

            File imageFile = new File(Environment.getExternalStoragePublicDirectory("/Pictures/findog"), "dogphoto.png");
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivity(i);
        }
        else {

            //  File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            imageFile = new File(Environment.getExternalStoragePublicDirectory("/Pictures/findog"), "dogphoto.png");
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivity(i);

        }

        File imgFile = new  File("/sdcard//Pictures/findog/dogphoto.png");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv.setImageBitmap(myBitmap);

        }

    }

    public void SetUpMapIfNeeded() {

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        if(mMap != null) {

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View v = getLayoutInflater().inflate(R.layout.info_window, null);
                    TextView tx = (TextView) v.findViewById(R.id.txt_view);
                    TextView tlat = (TextView) v.findViewById(R.id.txt_lat);
                    TextView tlng = (TextView) v.findViewById(R.id.txt_lng);
                    ImageView image = (ImageView) v.findViewById(R.id.image1);

                    Bitmap bmp = BitmapFactory.decodeFile("/sdcard/Pictures/findog/dogphoto.png");
                    image.setImageBitmap(bmp);

                    // LatLng ll = marker.getPosition();

                    tx.setText(marker.getTitle());
                    //tlat.setText(String.valueOf("My position: "+ll.latitude+" : "));
                    // tlng.setText(String.valueOf(ll.longitude));

                    return v;
                }
            });
        }

    }

    public void VaiClick(View view) {
        System.out.println("deu certo ?");
        mRef = new Firebase("https://fordog.firebaseio.com/");
        mRef.child("dogim").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshots) {
                System.out.println("entrou ");
                for( DataSnapshot dataSnapshot : dataSnapshots.getChildren() ){
                    Cachorro cachorro = dataSnapshot.getValue( Cachorro.class );
                    System.out.println( " Latitudes " + cachorro.getLatitude() + " Longitudes " + cachorro.getLongitude() );
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Integer.parseInt(cachorro.getLatitude()), Integer.parseInt(cachorro.getLongitude()))).title(String.valueOf(iv)));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc));

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}


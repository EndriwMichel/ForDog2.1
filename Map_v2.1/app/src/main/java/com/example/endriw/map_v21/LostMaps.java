package com.example.endriw.map_v21;

import android.app.AlertDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Endriw on 09/11/2016.
 */
public class LostMaps extends FragmentActivity implements OnMapReadyCallback {
    public String hashzin;
    private LatLng position;
    final int x = 0;
    public AlertDialog dialog;
    private static Firebase mRef;

    public byte[] imageAsBytes;

    public int CountFb;

    private GoogleMap mMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_maps);

        Bundle extras = getIntent().getExtras();
        Firebase.setAndroidContext(this);
        //  mRef = new Firebase("https://dog-603e7.firebaseio.com");
        hashzin = extras.getString("hash");
//        SetUpMapIfNeeded();

        System.out.println("valor de hashzin: "+hashzin);
//        fireB();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    protected void onResume() {
        super.onResume();
        SetUpMapIfNeeded();
        fireB();

    }

    public void SetUpMapIfNeeded() {
        if(mMap == null){
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.upMap)).getMap();
        }
        if (mMap != null) {
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);
                    TextView tx = (TextView) v.findViewById(R.id.txt_view);
                    ImageView image = (ImageView) v.findViewById(R.id.image1);
                    tx.setText(marker.getTitle());
                    image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                    return v;
                }
            });
            //----------------------------------------------------------------------------------------

        }
    }

    public void fireB() {

        mRef = new Firebase("https://dog-603e7.firebaseio.com/");
        CountFb = 0;
        mRef.child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot dataSnapshot : data.getChildren()) {

                    Emails email = dataSnapshot.getValue(Emails.class);
                    final String stEmail = email.getEmail();

                    mRef.child(stEmail).child("lostDog").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override

                        public void onDataChange(DataSnapshot dataSnapshots) {
                            System.out.println("valor de hashzin drentro: " + hashzin);
                            CountFb += dataSnapshots.getChildrenCount();

                            for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {

                                Cachorro cachorro = dataSnapshot.getValue(Cachorro.class);
                                if(cachorro.getDogHash().equals(hashzin)) {
                                    imageAsBytes = Base64.decode(cachorro.getDogFoto().getBytes(), Base64.DEFAULT);
                                    position = new LatLng(Double.parseDouble(cachorro.getLatitude()),
                                            Double.parseDouble(cachorro.getLongitude()));
                                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 15);
                                    mMap.addMarker(new MarkerOptions()
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                            .position(position)
                                            .title(cachorro.getDogDesc())
                                    );
                                    mMap.animateCamera(update);
                                    System.out.println("dogHashIF: " + cachorro.getDogHash());
                                    break;
                                }
//                                x = x+1;
                            }

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

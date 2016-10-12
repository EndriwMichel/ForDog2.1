package com.example.endriw.map_v21;

import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.collection.LLRBNode;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import android.content.Intent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UpdateMaps extends FragmentActivity implements OnMapReadyCallback {

    Random rand = new Random();

    private List<Circle> cirList = new ArrayList<Circle>();

    public String[] teste = {"Cadastrar um cachorro perdido", "Listar meus cachorros encontrados", "Opções do usuario", "Sair"};

    private List<ListViewMaps> custom = new ArrayList<ListViewMaps>();
    private int[] vetor = new int[]{R.drawable.bone, R.drawable.dogbone, R.drawable.doghouse, R.drawable.dogavatar};

    public DrawerLayout dl;
    public ListView lv;
    public String hashPhoto;
    private ProgressDialog progress;
    public String hashzin;
    public static double latitude;
    public static double longitude;
    private LatLng position;

    public AlertDialog dialog;
    public AlertDialog.Builder ad;
    private GoogleMap mMapUser;
    private CameraUpdate up;
    private Firebase mRef;

    private byte[] bytes;
    public byte[] imageAsBytes;

    public int CountFb;

    private GoogleMap mMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_maps);
        Bundle extras = getIntent().getExtras();
        mRef = new Firebase("https://dog-603e7.firebaseio.com");
        hashzin = extras.getString("hash");
        //imageAsBytes = extras.getByteArray("foto");
        SetUpMapIfNeeded();
      /*  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.upMap);
        mapFragment.getMapAsync(this);
*/
        System.out.println("valor de hashzin: "+hashzin);
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
        final Intent intent_info  = new Intent(UpdateMaps.this, Update_CadDog.class);
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
                tx.setText("teste");
                image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                return v;
            }
        });
    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            intent_info.setFlags(intent_info.FLAG_ACTIVITY_CLEAR_TOP);
            intent_info.putExtra("hash", hashzin);
            startActivity(intent_info);

        }

    });
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {
                marker.setSnippet("Escolha um novo ponto !");
                }

                @Override
                public void onMarkerDragEnd(final Marker marker) {
                    System.out.println("Posição mudada para:"+marker.getPosition().longitude+" : "+marker.getPosition().latitude);

                    //------------------------------------------alertDialog------------------------------
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateMaps.this);
                    builder.setMessage("Deseja alterar a localização do dog ?");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(UpdateMaps.this, "salvar firebase latitude e longitude",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        marker.remove();
                        fireB();
                        }
                    });
                builder.create();
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
                    String stEmail = email.getEmail();

                    mRef.child("vaanhalen00@gmail@com").child("ownDog").child(hashzin).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override

                        public void onDataChange(DataSnapshot dataSnapshots) {
                            System.out.println("valor de hashzin drentro: "+hashzin);
                            CountFb += dataSnapshots.getChildrenCount();

                                ownCachorro cachorro = dataSnapshots.getValue(ownCachorro.class);
                            imageAsBytes = Base64.decode(cachorro.getDogFoto().getBytes(), Base64.DEFAULT);
                      //      System.out.println("oi? :"+cachorro.getLatitude()+" : "+cachorro.getLongitude()+" nome: "+cachorro.getDogNome());
                            position = new LatLng(Double.parseDouble(cachorro.getLatitude()),
                                    Double.parseDouble(cachorro.getLongitude()));
                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(position, 15);
                                mMap.addMarker(new MarkerOptions()
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                            .position(position)
                                            .title(cachorro.getDogNome())
                                            .draggable(true)
                                             );
                            mMap.animateCamera(update);

                              /*      mapa.put(dogs.getId(), (String) cachorro.getDogFoto());
                                    id = dogs.getId();
                                    markerUser.put(id, "dogs");
*/

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

    public void UpdatePosition(){

    }
}



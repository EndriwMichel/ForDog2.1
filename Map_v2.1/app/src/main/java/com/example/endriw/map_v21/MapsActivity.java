package com.example.endriw.map_v21;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    Random rand = new Random();

    private List<Circle> cirList = new ArrayList<Circle>();

    public String[] teste = {"Cadastrar um cachorro perdido", "Listar meus cachorros encontrados", "Opções do usuario", "Sair"};

    private List<ListViewMaps> custom = new ArrayList<ListViewMaps>();
    private int[] vetor = new int[]{R.drawable.bone, R.drawable.dogbone, R.drawable.doghouse, R.drawable.dogavatar};

    public DrawerLayout dl;
    public ListView lv;
    public String hashPhoto;
    private ProgressDialog progress;

    public static double latitude;
    public static double longitude;
    private LatLng UserPosition;

    public AlertDialog dialog;
    public AlertDialog.Builder ad;

    private GoogleMap mMap;
    private GoogleMap mMapUser;
    private CameraUpdate up;
    private Firebase mRef;
    Bitmap myBitmap;

    private Intent i;

    private String hashOwn;
    private String hashLost;
    private String lostDog = "lostDog";
    private String ownDog = "ownDog";
    private String userDog;

    private CircleOptions circle;
    private Circle cir;

    private ImageButton Cambtn;
    private boolean suces = true;
    private File imageFile;
    private File imgFile;
    public String nick;

    private Firebase firebase;
    private String base64Image;
    private String[] base64vet;

    private Map<String, String> mapa = new HashMap<>();
    HashMap<String, String> markerUser = new HashMap<String, String>();
    public Map<String, String> smart = new HashMap<String, String>();

    public Map<String, String> smart_teste = new HashMap<String, String>();

    private Map<String, String> cirMap = new HashMap<String, String>();
    private Map<String, String> EmailMap = new HashMap<String, String>();

    private Marker dogs;
    private Marker user;

    String id = null;

    private byte[] bytes;
    public byte[] imageAsBytes;

    public int CountFb;

    public static String accountName;

    private static final int REQUEST_CODE_EMAIL = 1;
    private TextView textEmail;

    private GoogleApiClient mGoogleapiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

    /*    Intent intentSplash = new Intent(this, MapsActivity.class);
        startActivity(intentSplash);
        finish();
*/
        mGoogleapiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        getEmail();

        ArrayList<String> bora = new ArrayList<String>();

        for (int x = 0; x < teste.length; x++) {
            bora.add(x, teste[x]);
        }
        ArrayAdapter<String> adaptBora = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bora);

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        lv = (ListView) findViewById(R.id.drawerList);

        populateList();
        ArrayAdapter<ListViewMaps> adapter = new MyListViewMaps();

        lv.setAdapter(adapter);

        firebase.setAndroidContext(this);
        firebase = new Firebase("https://dog-603e7.firebaseio.com/");



     /*   lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {

                if (parent.getItemAtPosition(pos) == "Cadastrar Lost Dog") {
                    startActivity(intent);

                } else if (parent.getItemAtPosition(pos) == "eita porra") {
                    ad = new AlertDialog.Builder(MapsActivity.this);
                    ad.setMessage("ta saino da jaula u muonstro");
                    ad.show();
                } else if (parent.getItemAtPosition(pos) == "Hurgggg") {
                    ad = new AlertDialog.Builder(MapsActivity.this);
                    ad.setMessage("A qui é bori bilder porra");
                    ad.show();
                } else if (parent.getItemAtPosition(pos) == "sabeoqueéissoaqui?") {
                    ad = new AlertDialog.Builder(MapsActivity.this);
                    ad.setMessage("Trapéziu des sem dente");
                    ad.show();
                }

                dl.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);

                    }
                });
            }
        });

*/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://dog-603e7.firebaseio.com");
        //-------------------------------------------------smart----------------------------
       // buscarDogNoBanco();
        //-------------------------------------------------smart----------------------------
       // smart_teste.put(vaiDados.put("dogCor", "Branco"), vaiDados.put("dogPorte", "Grande"));
       // System.out.println("vai dados: " + vaiDados);
       // System.out.println("vai smart teste "+smart_teste);
        Cambtn = (ImageButton) findViewById(R.id.imageButton);

        final Intent intent = new Intent(this, InitialCadDog.class);
        final Intent intent_user = new Intent(this, UserOptions.class);
        final Intent intent_lost = new Intent(this, InitialLostDog.class);

        ListView lv = (ListView) findViewById(R.id.drawerList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListViewMaps lvm = custom.get(position);

                if (lvm.getDex() == "Cadastrar um cachorro perdido") {
                    startActivity(intent);
                } else if (lvm.getDex() == "Opções do usuario") {
                    startActivity(intent_user);
                } else if (lvm.getDex() == "Listar meus cachorros encontrados") {
                    startActivity(intent_lost);
                }else if(lvm.getDex() == "Sair"){
                    try {
                        this.finalize();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }

                dl.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);

                    }
                });
            }
        });
//-------------------------------------------------------------------------------------------------------------
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleapiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
          mGoogleapiClient.disconnect();
    }


    private void populateList() {

        for (int x = 0; x < vetor.length; x++) {

            custom.add(new ListViewMaps(teste[x], vetor[x]));

        }
    }

    private class MyListViewMaps extends ArrayAdapter<ListViewMaps> {
        public MyListViewMaps() {
            super(MapsActivity.this, R.layout.listviewmaps, custom);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listviewmaps, parent, false);
            }
            ListViewMaps cus = custom.get(position);

            ImageView imgview = (ImageView) itemView.findViewById(R.id.img);
            imgview.setImageResource(cus.getImg());

            TextView textview = (TextView) itemView.findViewById(R.id.num);
            textview.setText(cus.getDex());

            return itemView;
        }
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

    public void ActionCamera(View view) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/Pictures/findog");
        // File direct2 = new File("/sdcard/");

        if (!direct.exists()) {

            suces = direct.mkdir();

            File imageFile = new File(Environment.getExternalStoragePublicDirectory("/Pictures/findog"), "dogphoto.png");
            i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(i, 3);

        } else {

            //  File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            imageFile = new File(Environment.getExternalStoragePublicDirectory("/Pictures/findog"), "dogphoto.png");
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(i, 2);

        }

    /*    imgFile = new File("/sdcard//Pictures/findog/dogphoto.png");

        if (imgFile.exists()) {


            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
           //     iv.setImageBitmap(myBitmap);

            final Intent intentCad = new Intent(this, LostDog.class);


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bytearray = stream.toByteArray();

                    intentCad.putExtra("latitude", latitude);
                    intentCad.putExtra("longitude", longitude);
                    intentCad.putExtra("bitmap", bytearray);
                    startActivity(intentCad);
                }
*/
        }

        public void SetUpMapIfNeeded() {
        final Intent intent_info  = new Intent(this, InfoWindowPhoto.class);
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                /*if(cir!=null){
                    cir.remove();
                    cir = null;
                    System.out.println("valor de cir:"+cir);
                }*/

                if(cir!=null) {
                    for (Circle circle : cirList) {
                        circle.remove();
                    }
                    cirList.clear();
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String m = markerUser.get(marker.getId());
                if(m.equals("dogs")) {

                    if (cir != null) {
                        for (Circle circle : cirList) {
                            circle.remove();
                        }
                        cirList.clear();
                    }

                    circle = new CircleOptions()
                            .center(marker.getPosition())
                            .radius(500)
                            .strokeWidth(1)
                            .fillColor(Color.argb(20, 50, 0, 255));
                    cir = mMap.addCircle(circle);
                    // cir.setVisible(true);
                    cirList.add(cir);
                    System.out.println("id cir: " + cir.getId());

                    cirMap.put(marker.getId(), cir.getId());
                }else if(m.equals("user")){
                    System.out.println("oi eu sou o usuario!");
                }
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //aqui
/*                String userEmail = marker.getTitle();
                String[] valorNuncaFixo = userEmail.split("%");
 */               intent_info.putExtra("DogFoto", imageAsBytes);
                intent_info.putExtra("UserEmail", EmailMap.get(marker.getId()));

                //  intent_info.putExtra("hash", )
                startActivity(intent_info);
            }
        });

        if (mMap != null) {

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    String m = markerUser.get(marker.getId());

                    if(m.equals("dogs")) {
                        View v = getLayoutInflater().inflate(R.layout.info_window, null);
                        TextView tx = (TextView) v.findViewById(R.id.txt_view);
                        TextView tx_dog = (TextView) v.findViewById(R.id.txt_dog);
                        TextView tx_user = (TextView) v.findViewById(R.id.txt_email);
                        ImageView image = (ImageView) v.findViewById(R.id.image1);
                        userDog = tx_user.getText().toString();

                        imageAsBytes = Base64.decode(mapa.get(marker.getId()).getBytes(), Base64.DEFAULT);
                        image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                       // Intent intent = new Intent(null, InfoWindowPhoto.class);
                       // intent.putExtra("DogFoto", imageAsBytes);

                        tx.setText(marker.getTitle());
                        return v;
                    }else if (m.equals("user")){
                        View v = getLayoutInflater().inflate(R.layout.user_info_window, null);
                        TextView tx = (TextView) v.findViewById(R.id.user_txt_view);
                        tx.setText("Você está aqui");
                        return v;
                    }

                        return null;

                }
            });
        }

    }

    public void fireB() {

        progress = ProgressDialog.show(this, "Exemplo", "Carregando mapa...", false, true);

        new Thread(new Runnable() {
            @Override
            public void run() {

        //começa o run--------------------------------------------------------------------------

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

                            CountFb += dataSnapshots.getChildrenCount();

                            for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {

                                Cachorro cachorro = dataSnapshot.getValue(Cachorro.class);

                                hashLost = cachorro.getDogHash();
                                dogs = mMap.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purppet))
                                                .position(new LatLng(Double.parseDouble(cachorro.getLatitude()),
                                                        Double.parseDouble(cachorro.getLongitude())))
                                                .title(cachorro.getDogDesc())
                                );
                                mapa.put(dogs.getId(), (String) cachorro.getDogFoto() );
                                EmailMap.put(dogs.getId(), (String) stEmail);
                                id = dogs.getId();

                                markerUser.put(id, "dogs");

                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    mRef.child(stEmail).child("ownDog").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshots) {

                            CountFb += dataSnapshots.getChildrenCount();
                            for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {

                                Cachorro cachorro = dataSnapshot.getValue(Cachorro.class);

                                hashOwn = cachorro.getDogHash();
                                dogs = mMap.addMarker(new MarkerOptions().
                                        icon(BitmapDescriptorFactory.fromResource(R.drawable.redpet))
                                        .position(new LatLng(Double.parseDouble(cachorro.getLatitude()),
                                                Double.parseDouble(cachorro.getLongitude())))
                                        .title(cachorro.getDogNome()));

                                mapa.put(dogs.getId(), (String) cachorro.getDogFoto() );
                                EmailMap.put(dogs.getId(), (String) stEmail);
                                id = dogs.getId();
                                markerUser.put(id, "dogs");
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
            //aqui termina o run------------------------------------------------------------------
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                });

            }
        }).start();

    }


    @Override
    public void onLocationChanged(Location location) {
        user.remove();

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        UserPosition = new LatLng(latitude, longitude);
       // CameraUpdate update = CameraUpdateFactory.newLatLngZoom(UserPosition, 15);
        user = mMap.addMarker(new MarkerOptions().position(UserPosition)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
       // mMap.animateCamera(update);

        id = user.getId();
        markerUser.put(id, "user");

    }


    @Override
    public void onConnected(Bundle bundle) {
 /*       Toast.makeText(this, "Connected !!",
                Toast.LENGTH_LONG).show();
*/
        AddUserMarker();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Suspended !!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("não rolou");
        Toast.makeText(this, "Connected Failed!!",
                Toast.LENGTH_LONG).show();
    }


    public void getEmail() {
        textEmail = (TextView)findViewById(R.id.textEmail);
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_EMAIL);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EMAIL && resultCode == RESULT_OK) {
           accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            textEmail.setText(accountName);

        }else if(requestCode == 2 && resultCode == RESULT_OK){
           imageLost();
            i = null;
        }else if(requestCode == 3 && resultCode == RESULT_OK){
            imageLost();
            i = null;
        }

    }
    protected void startLocationUpdates(){
        Log.d("TAG","startLocationUpdates");

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2500);//1s
        mLocationRequest.setFastestInterval(2500);//1s
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleapiClient,mLocationRequest,this);
    }
    protected void stopLocationUpdates(){
        Log.d("TAG", "stopLocationUpdates");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleapiClient, this);
    }

    public void AddUserMarker(){
        startLocationUpdates();

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleapiClient);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        UserPosition = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(UserPosition, 15);
        user = mMap.addMarker(new MarkerOptions().position(UserPosition)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.animateCamera(update);

        id = user.getId();
        markerUser.put(id, "user");
    }

    public void imageLost(){

        imgFile = new File("/sdcard//Pictures/findog/dogphoto.png");
        String namePath = "/sdcard//Pictures/findog/dogphoto.png";
        if (imgFile.exists()) {

            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            //     iv.setImageBitmap(myBitmap);

            final Intent intentCad = new Intent(this, LostDog.class);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bytearray = stream.toByteArray();

            intentCad.putExtra("latitude", latitude);
            intentCad.putExtra("longitude", longitude);
            intentCad.putExtra("bitmap", namePath);
            startActivity(intentCad);
        }
    }

    public void buscarDogNoBanco(){
        mRef.child("vaanhalen00@gmail@com").child("ownDog").addValueEventListener(new ValueEventListener() {

//            int y=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Cachorro cachorro = dataSnapshot1.getValue(Cachorro.class);

                    Map<String, String> vaiDados = new HashMap<>();
                    vaiDados.put("dogCor", cachorro.getDogCor());
                    vaiDados.put("dogPorte", cachorro.getDogPorte());
                    smartDog smtDog = new smartDog();
                    //smtDog.buscarDogNoBanco(vaiDados, mRef);
                }
            //    y++;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


}


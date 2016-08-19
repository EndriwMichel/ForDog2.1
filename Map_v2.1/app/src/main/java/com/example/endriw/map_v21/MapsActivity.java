package com.example.endriw.map_v21;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Random rand = new Random();

    public String[] teste = {"Cadastrar Lost Dog", "cadastrar2", "cadastrar3"};

    private List<ListViewMaps> custom = new ArrayList<ListViewMaps>();
    private int[] vetor = new int[]{R.drawable.bone, R.drawable.dogbone, R.drawable.doghouse};

    public DrawerLayout dl;
    public ListView lv;

    public AlertDialog dialog;
    public AlertDialog.Builder ad;

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

    private Firebase firebase;
    private String base64Image;
    private byte[] bytes;
    private byte[] imageAsBytes;

    private static final int REQUEST_CODE_EMAIL = 1;
    private TextView textEmail;

    //adenilda
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
        firebase = new Firebase("https://naelsorest.firebaseio.com");

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

        //aqui termina o codigo da barra de navegação fiotão

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Firebase.setAndroidContext(this);

        mRef = new Firebase("https://fordog.firebaseio.com/");
        Cambtn = (ImageButton) findViewById(R.id.imageButton);

        final Intent intent = new Intent(this, InitialCadDog.class);

        ListView lv = (ListView) findViewById(R.id.drawerList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListViewMaps lvm = custom.get(position);

                if (lvm.getDex() == "Cadastrar Lost Dog") {

                    startActivity(intent);
                } else if (parent.getItemAtPosition(position) == "cadastrar2") {
                    ad = new AlertDialog.Builder(MapsActivity.this);
                    ad.setMessage("ta saino da jaula u muonstro");
                    ad.show();
                } else if (parent.getItemAtPosition(position) == "cadastrar3") {
                    ad = new AlertDialog.Builder(MapsActivity.this);
                    ad.setMessage("A qui é bori bilder porra");
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

    }

    @Override
    public void onStart() {
        super.onStart();
        //mGoogleapiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        //  mGoogleapiClient.disconnect();

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
        // imagemFireBase();
        SetUpMapIfNeeded();
        fireB();
    }

    public void ActionCamera(View view) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/Pictures/findog");
        // File direct2 = new File("/sdcard/");

        if (!direct.exists()) {

            suces = direct.mkdir();

            File imageFile = new File(Environment.getExternalStoragePublicDirectory("/Pictures/findog"), "dogphoto.png");
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivity(i);
        } else {

            //  File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            imageFile = new File(Environment.getExternalStoragePublicDirectory("/Pictures/findog"), "dogphoto.png");
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivity(i);

        }

        File imgFile = new File("/sdcard//Pictures/findog/dogphoto.png");

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv.setImageBitmap(myBitmap);

        }

    }

    public void SetUpMapIfNeeded() {

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

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
                    TextView tlat = (TextView) v.findViewById(R.id.txt_lat);
                    TextView tlng = (TextView) v.findViewById(R.id.txt_lng);
                    ImageView image = (ImageView) v.findViewById(R.id.image1);

                    Bitmap bmp = BitmapFactory.decodeFile("/sdcard/Pictures/findog/dogphoto.png");
                    image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

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
        mRef = new Firebase("https://fordog.firebaseio.com/");
        mRef.child("dogim").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshots) {
                System.out.println("entrou ");
                for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
                    Cachorro cachorro = dataSnapshot.getValue(Cachorro.class);
                    System.out.println(" Latitudes " + cachorro.getLatitude() + " Longitudes " + cachorro.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Integer.parseInt(cachorro.getLatitude()), Integer.parseInt(cachorro.getLongitude()))).title(String.valueOf(iv)));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc));

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void fireB() {
        mRef = new Firebase("https://naelsorest.firebaseio.com/");

        mRef.child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot dataSnapshot : data.getChildren()) {

                    Emails email = dataSnapshot.getValue(Emails.class);
                    String stEmail = email.getEmail();

                    mRef.child(stEmail).child("lostDog").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshots) {

                            //usando um a um/
                            for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {

                                Cachorro cachorro = dataSnapshot.getValue(Cachorro.class);
                                System.out.println("getLatitude cachorro: " + cachorro.getLatitude());
                                System.out.println("getLongitude cachorro: " + cachorro.getLongitude());
                                System.out.println("getDogData cachorro: " + cachorro.getDogData());
                                System.out.println("getDogDesc cachorro: " + cachorro.getDogDesc());
                                System.out.println("getDogFoto cachorro: " + cachorro.getDogFoto());
                                System.out.println("getDogNome cachorro: " + cachorro.getDogNome());

                                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                                                                  .fromResource(R.drawable.marker)
                                               ).position(new LatLng(Integer.parseInt(cachorro.getLatitude()), Integer.parseInt(cachorro.getLongitude()))).title(String.valueOf(iv)));
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


   /* public void imagemFireBase(){
        firebase.child("imagem").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                base64Image = (String) dataSnapshot.getValue();
                imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);

               // imageButton.setImageBitmap(
                 //       BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                //);
                System.out.println("Downloaded image with length: " + imageAsBytes.length);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }*/

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Connected !!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Suspended !!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            textEmail.setText(accountName);
        }
    }
}


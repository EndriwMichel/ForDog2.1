package com.example.endriw.map_v21;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Endriw on 30/10/2016.
 */
public class smartOwn extends AppCompatActivity {


    private Firebase mRef;
    private int i = 0;
    private int y = 0;
    MapsActivity ma = new MapsActivity();
    final HashMap<String, String> mapa = new HashMap<String, String>();
    final static String[] vet_cor = new String[100];
    final static String[] vet_porte = new String[100];
    private int x = 0;

    ArrayAdapter<ListViewInitialCad> adapter;
    private String[] desc = new String[100];
    public byte[] imageAsBytes;
    public Bitmap[] bitarray = new Bitmap[100];
    public String [] teste_hash = new String[100];
    public String [] teste = new String[100];

    private List<ListViewInitialCad> custom = new ArrayList<ListViewInitialCad>();

    private int[] vetor = new int[]{R.drawable.bone};

    public ListView lv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_match);

        mRef = new Firebase("https://dog-603e7.firebaseio.com/");


        lv = (ListView) findViewById(R.id.ListDog);

    }

    protected void onResume() {
        super.onResume();
        custom.clear();
        FireB();
    }


    private class MyListViewMaps extends ArrayAdapter<ListViewInitialCad> {
        public MyListViewMaps() {
            super(smartOwn.this, R.layout.listviewmaps, custom);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listviewmaps, parent, false);
            }
            ListViewInitialCad cus = custom.get(position);

            ImageView imgview = (ImageView) itemView.findViewById(R.id.img);
            imgview.setImageBitmap(cus.getImg());

            TextView textview = (TextView) itemView.findViewById(R.id.num);
            textview.setText(cus.getDex());

            TextView textView_desc = (TextView) itemView.findViewById(R.id.data);
            textView_desc.setText(cus.getData());

            TextView textView_hash = (TextView) itemView.findViewById(R.id.hash);
            textView_hash.setText(cus.getHash());

            return itemView;
        }

    }




    public void FireB(){

        mRef = new Firebase("https://dog-603e7.firebaseio.com");
        //  public void buscarDogNoBanco(final Map<String, String> mapa, final Firebase mRef ) {

        final String[] teste_hash = new String[100];
        final String[] teste_email = new String[100];

        mRef.child("vaanhalen00@gmail@com").child("ownDog").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Cachorro cachorro = dataSnapshot1.getValue(Cachorro.class);

                    // Map<String, String> vaiDados = new HashMap<>();
                    mapa.put("dogCor", cachorro.getDogCor());
                    mapa.put("dogPorte", cachorro.getDogPorte());



                    vet_cor[y] = String.valueOf(cachorro.getDogCor());
                    vet_porte[y] = String.valueOf(cachorro.getDogPorte());

                    y++;

                    System.out.println("dog cor fora: "+mapa);

                    // smartDog smtDog = new smartDog();
                    //buscarDogNoBanco(vaiDados, mRef);
                }
                //    y++;
                System.out.println("vetor cor aleatoria: "+vet_cor[2]);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //-------------------------------------------------------------------------------------------------------------------------

        mRef.child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot dataSnapshot : data.getChildren()) {

                    Emails email = dataSnapshot.getValue(Emails.class);
                    final String stEmail = email.getEmail();
                    // System.out.println(stEmail);

                    mRef.child(stEmail).child("lostDog").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override

                        public void onDataChange(DataSnapshot dataSnapshots) {
                            boolean corMatch = false;
                            boolean porteMatch = false;
                            for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
//                                        for (i = 0; i <= vet_cor.length; i++) {
                                i = 0;
                                Cachorro dog = dataSnapshot.getValue(Cachorro.class);

                                String stDogCor = dog.getDogCor();
                                String stDogPorte = dog.getDogPorte();
//                                        String stMapDogCor = mapa.get("dogCor");
//                                        String stMapDogPorte = mapa.get("dogPorte");
                                for(i=0;i<=3;i++) {
                                    String stMapDogCor = vet_cor[i];
                                    String stMapDogPorte = vet_porte[i];

                                    System.out.println("dog cor dentro: " + vet_cor[i]);
                                    System.out.println("dog cor dentro: " + vet_porte[i]);

                                    if (stDogCor.trim().toLowerCase().equals(stMapDogCor.trim().toLowerCase())) {
                                        corMatch = true;
                                    } else {
                                        corMatch = false;
                                    }

                                    if (stMapDogPorte.trim().toLowerCase().equals(stDogPorte.trim().toLowerCase())) {
                                        porteMatch = true;
                                    } else {
                                        porteMatch = false;
                                    }

                                    // i = i + 1;
                                    if (porteMatch && corMatch) {


//------------------------------------Reutilização da classe InitialLostDog--------------------------------------------------

                                        teste_hash[x] = dog.getDogHash();
                                        teste[x] = dog.getDogData();
                                        desc[x] = dog.getDogDesc();
                                        imageAsBytes = Base64.decode(dog.getDogFoto().getBytes(), Base64.DEFAULT);
                                        bitarray[x] = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

                                        custom.add(new ListViewInitialCad(teste[x], teste_hash[x], bitarray[x], desc[x]));
//--------------------------------------------------------------------------------------------------------------------------
                                        teste_hash[x] = dog.getDogHash();
                                        System.out.println("bora vetor smartOwn: " + teste_hash[x]);
                                        x++;
                                    }
                                }
                            }
//------------------------------------Reutilização da classe InitialLostDog--------------------------------------------------

                            adapter = new MyListViewMaps();
                            lv.setChoiceMode(lv.CHOICE_MODE_SINGLE);
                            lv.setAdapter(adapter);

//------------------------------------Reutilização da classe InitialLostDog--------------------------------------------------

                        }
                        //                           }

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



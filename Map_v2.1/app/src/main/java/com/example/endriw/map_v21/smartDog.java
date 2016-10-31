package com.example.endriw.map_v21;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eder on 09/09/2016.
 */
public class smartDog extends IntentService{
//    public var
    private int i = 0;
    private int y = 0;
    private int x = 0;
    MapsActivity ma = new MapsActivity();
    final static HashMap<String, String> mapa = new HashMap<String, String>();
    final static String[] vet_cor = new String[3];
    final static String[] vet_porte = new String[3];
    Firebase mRef;

    public smartDog() {
        super("Serviço da hora");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        mapa.put("dogCor", "Branco");
//        mapa.put("dogPorte", "Grande");
//        System.out.println("dog cor fora:"+mapa.get("dogCor"));

        mRef = new Firebase("https://dog-603e7.firebaseio.com");
      //  public void buscarDogNoBanco(final Map<String, String> mapa, final Firebase mRef ) {

        final String[] teste_hash = new String[100];

//        mRef.child("vaanhalen00@gmail@com").child("ownDog").addValueEventListener(new ValueEventListener() {
//
//            //            int y=0;
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Cachorro cachorro = dataSnapshot1.getValue(Cachorro.class);
//
//                   //] Map<String, String> vaiDados = new HashMap<>();
//                    mapa.put("dogCor", cachorro.getDogCor());
//                    mapa.put("dogPorte", cachorro.getDogPorte());
//
//                    vet_cor[y] = String.valueOf(cachorro.getDogCor());
//                    vet_porte[y] = String.valueOf(cachorro.getDogPorte());
//
//                    System.out.println("dog cor fora: "+vet_cor[y]);
//
//                    // smartDog smtDog = new smartDog();
//                    //buscarDogNoBanco(vaiDados, mRef);
//                }
//                y++;
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

        //-------------------------------------------------------------------------------------------------------------------------

        mRef.child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot dataSnapshot : data.getChildren()) {

                    Emails email = dataSnapshot.getValue(Emails.class);
                    String stEmail = email.getEmail();
                    System.out.println(stEmail);

                        mRef.child(stEmail).child("lostDog").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshots) {

                                    for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
//                                        for (i = 0; i <= vet_cor.length; i++) {
                                        Cachorro dog = dataSnapshot.getValue(Cachorro.class);
                                        boolean corMatch = false;
                                        boolean porteMatch = false;
                                        String stDogCor = dog.getDogCor();
                                        String stDogPorte = dog.getDogPorte();
                                        String stMapDogCor = mapa.get("dogCor");
                                        String stMapDogPorte = mapa.get("dogPorte");
//                                        String stMapDogCor = vet_cor[i];
//                                        String stMapDogPorte = vet_porte[i];
                                        System.out.println("dog cor dentro: " + vet_cor[i]);

                                        if (stDogCor.trim().toLowerCase().equals(stMapDogCor.trim().toLowerCase())) {
                                            corMatch = true;
                                        }

                                        if (stMapDogPorte.trim().toLowerCase().equals(stDogPorte.trim().toLowerCase())) {
                                            porteMatch = true;
                                        }

                                        if (((stMapDogPorte.trim().toLowerCase().equals("pequeno")) && (stMapDogPorte.trim().toLowerCase().equals("medio"))) ||
                                                ((stMapDogPorte.trim().toLowerCase().equals("medio")) && (stMapDogPorte.trim().toLowerCase().equals("pequeno"))) ||
                                                ((stMapDogPorte.trim().toLowerCase().equals("medio")) && (stMapDogPorte.trim().toLowerCase().equals("grande"))) ||
                                                ((stMapDogPorte.trim().toLowerCase().equals("grande")) && (stMapDogPorte.trim().toLowerCase().equals("medio")))) {
                                            porteMatch = true;
                                        }

                                       // i = i + 1;
                                        if (porteMatch && corMatch) {
                                            teste_hash[x] = dog.getDogHash();
                                            System.out.println("bora vetor: " + teste_hash[x]);
                                            x++;
                                        }

                                    }
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


   // }
     Noty.NotificationUtil.create(this, 1, intent, "teste", "teste");
//        for (i = 0; i <= vet_cor.length; i++) {
//            System.out.println("dog cor dentro: " + vet_cor[i]);
//        }

    }

}

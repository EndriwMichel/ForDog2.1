package com.example.endriw.map_v21;

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
public class smartDog {
//    public var
    private int i = 0;
    static int x = 0;
    MapsActivity ma = new MapsActivity();

    public void buscarDogNoBanco(final Map<String, String> mapa, final Firebase mRef ) {

        final String[] teste_hash = new String[100];

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

                                Cachorro dog = dataSnapshot.getValue(Cachorro.class);
                                boolean corMatch = false;
                                boolean porteMatch = false;
                                String stDogCor = dog.getDogCor();
                                String stDogPorte = dog.getDogPorte();
                                String stMapDogCor = mapa.get("dogCor");
                                String stMapDogPorte = mapa.get("dogPorte");

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

                                i = i + 1;
                                if (porteMatch && corMatch) {
                                    teste_hash[x] = dog.getDogHash();
                                    System.out.println("bora vetor: "+teste_hash[x]);
                                    x++;
                                }

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

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
    public void buscarDogNoBanco(final Map<String, String> mapa, final Firebase mRef ){

        final Map<String, String> regDog = new HashMap<>();

        mRef.child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot dataSnapshot : data.getChildren()) {

                    Emails email = dataSnapshot.getValue(Emails.class);
                    String stEmail = email.getEmail();

                    mRef.child(stEmail).child("lostDog").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override

                        public void onDataChange(DataSnapshot dataSnapshots) {

                            for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {

                                Cachorro dog = dataSnapshot.getValue(Cachorro.class);
                                boolean corMatch = false;
                                boolean porteMatch = false;

                                if( dog.getDogCor() == mapa.get("dogCor") )
                                    corMatch = true;

                                if( dog.getDogPorte() == mapa.get("dogPorte") ) {
                                    porteMatch = true;
                                }

                                if( (( mapa.get("dogPorte") == "pequeno" ) && ( dog.getDogPorte() == "medio")) ||
                                        (( mapa.get("dogPorte") == "medio" ) && ( dog.getDogPorte() == "pequeno")) ||
                                        (( mapa.get("dogPorte") == "medio" ) && ( dog.getDogPorte() == "grande"))  ||
                                        (( mapa.get("dogPorte") == "grande" ) && ( dog.getDogPorte() == "medio"))    ) {
                                    porteMatch = true;
                                }
                                i = i+1;
                                if( porteMatch && corMatch ) {
                                    regDog.put("hash" + i, dog.getDogHash());
                                    System.out.println( "teste hash " + regDog.get("hash" + i));
                                }
                                System.out.println( "nada");
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

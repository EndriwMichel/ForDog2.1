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

    public void buscarDogNoBanco(final Map<String, String> mapa ){

        final Firebase mRef = new Firebase("https://dog-603e7.firebaseio.com/");
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
                                boolean racaMatch = false;

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

                                if( dog.getDogRaca() == mapa.get("dogRaca") )
                                    racaMatch = true;

                                String msg = "Encontramos o cadastro deste dog em nosso banco de dados por ter "
                                        + (racaMatch ? ("a raça") : "") + ( porteMatch ? ("o porte") : "")
                                        + "compativel com o seu dog perdido.";

                                regDog.put("latitude", dog.getLatitude());
                                regDog.put("longitude", dog.getLongitude());
                                regDog.put("hash", dog.getDogHash());
                                //regDog.put("email", dog.getdogEmail());
                                regDog.put("cel", dog.getDogCel());
                                regDog.put("mensagem", msg);
                                //não pare o loop, outras pessoas podem ter cadastrado o mesmo animal por engano ou pq é bocoh mesmo.

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

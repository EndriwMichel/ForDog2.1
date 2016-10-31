package com.example.endriw.map_v21;

import android.app.IntentService;
import android.content.Intent;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Endriw on 30/10/2016.
 */
public class SmartOwn extends IntentService {
    Firebase mRef;
    final static String[] vet_cor = new String[3];
    final static String[] vet_porte = new String[3];
    private int y = 0;
    public Intent smart;

    public SmartOwn(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        mRef = new Firebase("https://dog-603e7.firebaseio.com");
        smart = new Intent(this, smartDog.class);
                mRef.child("vaanhalen00@gmail@com").child("ownDog").addValueEventListener(new ValueEventListener() {

            //            int y=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Cachorro cachorro = dataSnapshot1.getValue(Cachorro.class);

                   //] Map<String, String> vaiDados = new HashMap<>();
                    vet_cor[y] = String.valueOf(cachorro.getDogCor());
                    vet_porte[y] = String.valueOf(cachorro.getDogPorte());

                    smart.putExtra("cor", vet_cor[y]);
                    smart.putExtra("porte", vet_cor[y]);

                    // smartDog smtDog = new smartDog();
                    //buscarDogNoBanco(vaiDados, mRef);
                    System.out.println("dog cor fora own: "+vet_cor[y]);
                }
                    y++;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}

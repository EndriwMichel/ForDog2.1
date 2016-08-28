package com.example.endriw.map_v21;

import com.firebase.client.Firebase;

/**
 * Created by eder on 26/08/2016.
 */
public class dogFirebase {

    public void gravaFirebase(String email, int key, String dogNome, String dogDesc, String dogData, String latitude, String longitude,
                              String dogFoto, String dogcel, String dogPorte, String dogCor) {
        Firebase mRef = new Firebase("https://dog-603e7.firebaseio.com/");

        email = (email != "") ? email : "email";

        mRef.child("emails").child(String.valueOf(email.hashCode())).child("email").setValue(email);

        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogNome").setValue(dogNome);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogDesc").setValue(dogDesc);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogData").setValue(dogData);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("latitude").setValue(latitude);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("longitude").setValue(longitude);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogFoto").setValue(dogFoto);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogHash").setValue(String.valueOf(key));
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogCel").setValue( dogcel );
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogPorte").setValue( dogPorte );
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogCor").setValue( dogCor );


        //onwDog
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogNome").setValue(dogNome);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogDesc").setValue(dogDesc);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogData").setValue(dogData);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("latitude").setValue(latitude);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("longitude").setValue(longitude);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogFoto").setValue(dogFoto);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogHash").setValue(String.valueOf(key));
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogCel").setValue( dogcel );
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogPorte").setValue( dogPorte );
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogCor").setValue( dogCor );

    }

}

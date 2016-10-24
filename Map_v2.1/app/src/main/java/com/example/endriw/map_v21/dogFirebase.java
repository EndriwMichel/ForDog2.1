package com.example.endriw.map_v21;

import com.firebase.client.Firebase;

/**
 * Created by eder on 26/08/2016.
 */
public class dogFirebase {

    public void gravaOwn(String email, int key, String dogNome, String dogData, String latitude, String longitude,
                              String dogFoto, String dogcel, String dogPorte, String dogCor) {
        Firebase mRef = new Firebase("https://dog-603e7.firebaseio.com/");

        email = (email != "") ? email : "email";

        mRef.child("emails").child(String.valueOf(email.hashCode())).child("email").setValue(email);

        //onwDog
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogNome").setValue(dogNome);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogData").setValue(dogData);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("latitude").setValue(latitude);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("longitude").setValue(longitude);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogFoto").setValue(dogFoto);
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogHash").setValue(String.valueOf(key));
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogCel").setValue( dogcel );
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogPorte").setValue( dogPorte );
        mRef.child(email).child("ownDog").child(String.valueOf(key)).child("dogCor").setValue( dogCor );

    }
    public void gravaLost(String email, int key, String dogDesc, String dogData, String latitude, String longitude,
                              String dogFoto, String dogcel, String dogPorte, String dogCor) {

        Firebase mRef = new Firebase("https://dog-603e7.firebaseio.com/");
        email = (email != "") ? email : "email";

        mRef.child("emails").child(String.valueOf(email.hashCode())).child("email").setValue(email);

        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogDesc").setValue(dogDesc);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogData").setValue(dogData);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("latitude").setValue(latitude);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("longitude").setValue(longitude);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogFoto").setValue(dogFoto);
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogHash").setValue(String.valueOf(key));
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogCel").setValue( dogcel );
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogPorte").setValue( dogPorte );
        mRef.child(email).child("lostDog").child(String.valueOf(key)).child("dogCor").setValue( dogCor );

    }

    public void graveUser(String email, String dogNick, String dogCel, String dogNotify){
        Firebase mRef = new Firebase("https://dog-603e7.firebaseio.com/");
        email = (email != "") ? email : "email";

        mRef.child(email).child("userDog").child("dogCel").setValue(dogCel);
        mRef.child(email).child("userDog").child("dogNick").setValue(dogNick);
        mRef.child(email).child("userDog").child("dogNotify").setValue(dogNotify);
    }

    public void deleteDog(String email, String dog, String key, Firebase mRef) {

        mRef.child(email).child(dog).child(key).removeValue();

    }

        public void updateDog(Firebase mRef, String email, String hash, String chave, String valor) {
            mRef.child(email).child("ownDog").child(hash).child(chave).setValue(valor);
        }

      /*  Exemplo update:
        GravaFirebase gravaFireBase = new GravaFireBase();
        gravaFireBase.updateDog( mRef, emails, hash, "latitude", "12" );
        gravaFireBase.updateDog( mRef, emails, hash, "longitude", "12" );
        */

}

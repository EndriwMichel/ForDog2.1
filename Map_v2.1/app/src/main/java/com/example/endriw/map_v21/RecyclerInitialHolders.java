package com.example.endriw.map_v21;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RecyclerInitialHolders extends RecyclerView.ViewHolder{

    public TextView personName;
    public TextView personAddress;
    public ImageView personPhoto;
    public Context mContext;

    private Firebase mRef;

    public RecyclerInitialHolders(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        personName = (TextView)itemView.findViewById(R.id.person_name);
        personAddress = (TextView)itemView.findViewById(R.id.person_address);
        personPhoto = (ImageView)itemView.findViewById(R.id.circleView);

        final InitialCadDog ic = new InitialCadDog();

        Picasso.with(mContext)
                .load("https://firebasestorage.googleapis.com/v0/b/dog-603e7.appspot.com/o/%C3%ADndice.png?alt=media&token=2a65cdd1-84ba-4e31-94aa-0cc7c5b62221")
                .resize(200, 200)
                .centerCrop()
                .into(personPhoto);

        mRef = new Firebase("https://dog-603e7.firebaseio.com/");

        mRef.child("vaanhalen00@gmail@com").child("ownDog").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                int x = 1;
                    System.out.println("valor de x: "+x);

                        Cachorro cachorro = dataSnapshot1.getValue(Cachorro.class);
                    System.out.println(cachorro.getDogNome());
                    ic.teste[x] = cachorro.getDogNome();
                    ic.teste2[x] = cachorro.getDogData();

                    personName.setText(ic.teste[x]);
                    personAddress.setText(ic.teste2[x]);
                x=+1;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}

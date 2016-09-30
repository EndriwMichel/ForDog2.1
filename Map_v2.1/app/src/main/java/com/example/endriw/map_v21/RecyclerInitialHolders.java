package com.example.endriw.map_v21;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerInitialHolders extends RecyclerView.ViewHolder{

    public TextView personName;
    public TextView personAddress;
    public ImageView personPhoto;

    public RecyclerInitialHolders(View itemView) {
        super(itemView);

        personName = (TextView)itemView.findViewById(R.id.person_name);
        personAddress = (TextView)itemView.findViewById(R.id.person_address);
        personPhoto = (ImageView)itemView.findViewById(R.id.circleView);
    }
}

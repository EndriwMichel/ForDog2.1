package com.example.endriw.map_v21;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerInitial extends RecyclerView.Adapter<RecyclerInitialHolders> {

    private List<ItemObjectInitial> itemList;
    private Context context;
    public RecyclerInitial(Context context, List<ItemObjectInitial> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerInitialHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.initial_list, null);
        RecyclerInitialHolders rcv = new RecyclerInitialHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerInitialHolders holder, int position) {
        holder.personName.setText(itemList.get(position).getName());
        holder.personAddress.setText(itemList.get(position).getAddress());
        holder.personPhoto.setImageResource(itemList.get(position).getPhotoId());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

package com.example.endriw.map_v21;

import android.graphics.Bitmap;

/**
 * Created by Endriw on 18/08/2016.
 */
public class ListViewInitialCad {

    private String dex;
    private String hash;
    private Bitmap img;


    public ListViewInitialCad(String dex, String hash, Bitmap img){
        super();
        this.dex = dex;
        this.hash = hash;
        this.img = img;
    }

    public ListViewInitialCad(){

    }

    public String getDex() {
        return dex;
    }

    public void setDex(String dex) {
        this.dex = dex;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getHash(){
        return hash;
    }

    public void setHash(String hash){
        this.hash = hash;
    }
}

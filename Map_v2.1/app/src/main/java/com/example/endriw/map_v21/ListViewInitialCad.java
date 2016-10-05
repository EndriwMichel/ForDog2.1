package com.example.endriw.map_v21;

import android.graphics.Bitmap;

/**
 * Created by Endriw on 18/08/2016.
 */
public class ListViewInitialCad {

    private String dex;
    private Bitmap img;


    public ListViewInitialCad(String dex, Bitmap img){
        super();
        this.dex = dex;
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

}

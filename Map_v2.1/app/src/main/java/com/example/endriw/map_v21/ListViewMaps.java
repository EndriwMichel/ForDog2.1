package com.example.endriw.map_v21;

import android.widget.ImageView;

/**
 * Created by Endriw on 14/07/2016.
 */
public class ListViewMaps {

    private String dex;
    private int img;


    public ListViewMaps(String dex, int img){
        super();
        this.dex = dex;
        this.img = img;
    }

    public ListViewMaps() {

    }

    public ListViewMaps(String dex, ImageView imageView) {

    }


    public String getDex() {
        return dex;
    }

    public void setDex(String dex) {
        this.dex = dex;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

}

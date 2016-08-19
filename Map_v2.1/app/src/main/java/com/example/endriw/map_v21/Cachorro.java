package com.example.endriw.map_v21;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * Created by Endriw on 01/06/2016.
 */
public class Cachorro {

    private String latitude;
    private String longitude;
    private String dogData;
    private String dogDesc;
    private String dogFoto;
    private String dogNome;

    public Cachorro(){
    }

    public Cachorro(String latitude, String longitude, String dogData, String dogDesc, String dogFoto, String dogNome){
        this.latitude = latitude;
        this.longitude = longitude;
        this.dogData = dogData;
        this.dogDesc = dogDesc;
        this.dogFoto = dogFoto;
        this.dogNome = dogNome;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDogData() {
        return dogData;
    }

    public String getDogDesc() {
        return dogDesc;
    }

    public String getDogFoto() {
        return dogFoto;
    }

    public String getDogNome() {
        return dogNome;
    }
}

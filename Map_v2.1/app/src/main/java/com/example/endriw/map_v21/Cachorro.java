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
    private String dogHash;
    private String dogCor;
    private String dogPorte;
    private String dogRaca;
    private String dogCel;
    private String dogNick;
    private String dogNotify;

    public Cachorro(){
    }

    public Cachorro(String latitude, String longitude, String dogData, String dogDesc, String dogFoto, String dogNome, String dogHash, String dogCor, String dogRaca, String dogPorte, String dogCel, String dogNick, String dogNotify){
        this.latitude  = latitude;
        this.longitude = longitude;
        this.dogData   = dogData;
        this.dogDesc   = dogDesc;
        this.dogFoto   = dogFoto;
        this.dogNome   = dogNome;
        this.dogHash   = dogHash;
        this.dogCor    = dogCor;
        this.dogPorte  = dogPorte;
        this.dogRaca    = dogRaca;
        this.dogCel    = dogCel;
        this.dogNick = dogNick;
        this.dogNotify = dogNotify;
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

    public String getDogHash() {
        return dogHash;
    }

    public String getDogCor() {
        return dogCor;
    }

    public String getDogPorte() {
        return dogPorte;
    }

    public String getDogRaca() {
        return dogRaca;
    }

    public String getDogCel() {
        return dogCel;
    }

    public String getDogNick(){
        return dogNick;
    }
    public String getDogNotify(){
        return dogNotify;
    }
}

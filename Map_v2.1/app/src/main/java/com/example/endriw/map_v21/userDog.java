package com.example.endriw.map_v21;


public class userDog {

    private String dogCel;
    private String dogNick;
    private String dogNotify;

    public userDog(){

    }

    public userDog(String dogCel, String dogNick, String dogNotify){
        this.dogCel = dogCel;
        this.dogNick = dogNick;
        this.dogNotify = dogNotify;
    }

    public String getDogCel(){
        return dogCel;
    }
    public String getDogNick(){
        return dogNick;
    }
    public String getDogNotify(){
        return dogNotify;
    }
}

package com.example.endriw.map_v21;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * Created by Endriw on 01/06/2016.
 */
public class Cachorro {

    private String latitude;
    private String longitude;

    public Cachorro (){
    }

    @JsonIgnore
    public Cachorro (String latitude, String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getLatitude() {
        return latitude;
    }

    /*public void setLatitude(String latitude) {
        this.latitude = latitude;
    } */

    public String getLongitude() {
        return longitude;
    }

/*    public void setLongitude(String longitude) {
        this.longitude = longitude;
    } */

}

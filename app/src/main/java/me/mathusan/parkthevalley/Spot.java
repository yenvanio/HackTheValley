package me.mathusan.parkthevalley;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Mathu on 2017-01-07.
 */

public class Spot {


    double lat;
    double lng;
    boolean open;

    public Spot() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public double getLat() {return lat;}

    public double getLng() {return lng;}

    public void setLat(double lat) {this.lat=lat;}

    public void setLng(double lng) {this.lng=lng;}

}
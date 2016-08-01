package com.knitechs.www.ss12;

import android.util.Log;

import com.knitechs.www.ss12.core.GEOLocation;
import com.knitechs.www.ss12.core.JSONParser;
import com.knitechs.www.ss12.core.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Koli on 8/1/2016.
 */
public class AppUser {

    private String userID;
    private String username;
    private String address;
    private String contact;
    private double lat;
    private double lng;
    private boolean ringed;
    private String ip;
    private String imgepath;

    private static AppUser instance;

    public static AppUser getInstance(){
        if(instance == null)
            instance= new AppUser();
        return instance;
    }

    private AppUser(){
        this.userID ="1";
        this.username ="kolitha";
        this.address="mawanella";
        this.contact="0718766764";
        this.ip = GEOLocation.getInstance().getOwnIP();
        this.ringed=false;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public boolean isRinged() {
        return ringed;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getImgepath() {
        return imgepath;
    }

    public boolean updateLatLng(){

        int success;
        try {

            HashMap<String,String> parameters = new HashMap<>();

            parameters.put("UID",this.userID);
            parameters.put("LAT",String.valueOf(this.lat));
            parameters.put("LNG",String.valueOf(this.lng));

            JSONObject json = JSONParser.getInstance().makeHttpRequest(URLs.updateLatLng, "POST", parameters);

            // check your log for json response
            Log.d("Single Object", json.toString());

            success = json.getInt("success");
            if (success == 1) {
                return true;
            }else{
               return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String getIp() {
        return ip;
    }
}

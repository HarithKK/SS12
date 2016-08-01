package com.knitechs.www.ss12.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

import com.knitechs.www.ss12.AppUser;

/**
 * Created by Koli on 7/31/2016.
 */
public class GEOLocation extends Service implements LocationListener {

    private static GEOLocation instance;
    private Context context;
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private Location location;
    private double latitude;
    private double longitude;
    private boolean isupdating=false;

    public static GEOLocation getInstance(Context context){
        if(instance==null){
            instance = new GEOLocation(context);
        }
        return instance;
    }

    public static GEOLocation getInstance(){
        if(instance!=null){
            return instance;
        }
        return null;
    }

    private GEOLocation(){}
    private GEOLocation(Context c){
        this.context = c;
        this.locationManager = (LocationManager) c.getSystemService(LOCATION_SERVICE);
        // check GPS and NETWORK

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled =locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(isGPSEnabled()){
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,1,this);
        }else if(isNetworkEnabled()){
            this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,1,this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {

        this.latitude = location.getLatitude();
        this.longitude =location.getLongitude();

        AppUser.getInstance().setLat(this.latitude);
        AppUser.getInstance().setLng(this.longitude);

        if(!isupdating){
            new UpdateLatLng().execute();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public Location getLocation() {
        try {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled() || isNetworkEnabled()) {

                if (isNetworkEnabled()) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1,1, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled()) {
                    location=null;
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLatitudeString() {
        return String.valueOf(latitude);
    }

    public String getLongitudeString() {
        return String.valueOf(longitude);
    }

    public boolean isGPSEnabled() {
        return isGPSEnabled;
    }

    public boolean isNetworkEnabled() {
        return isNetworkEnabled;
    }

    public double getDistance(double lat,double lng){
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat-this.latitude);
        double lngDiff = Math.toRadians(lng-this.longitude);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(lat)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return (new Double(distance * meterConversion).doubleValue())/1000;
    }

    class UpdateLatLng extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isupdating = true;
        }

        @Override
        protected String doInBackground(String... params) {
            AppUser.getInstance().updateLatLng();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            isupdating=false;
        }

    }

    public String getOwnIP(){
        WifiManager wifiMgr = (WifiManager)context.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return android.text.format.Formatter.formatIpAddress(ip);
    }
}

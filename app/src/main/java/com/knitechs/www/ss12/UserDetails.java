package com.knitechs.www.ss12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.knitechs.www.ss12.core.GEOLocation;
import com.knitechs.www.ss12.core.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by Koli on 7/30/2016.
 */
public class UserDetails {

    private String uid;
    private String name;
    private String address;
    private String mobile;
    private double lat;
    private double lng;
    private int bell;
    private String imagepath;
    private String ip;

    private Bitmap image;
    private Context context;
    private double diatance;

    public UserDetails(Context context,String name,int imageresouce,String mobile,String address,String distance){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),imageresouce);
        //crop
        this.image = ImageConverter.getCroppedBitmap(bitmap);
        this.name = name;
        this.context = context;
        this.mobile=mobile;
        this.address=address;
        this.diatance = distance.isEmpty() ?0.0:Double.parseDouble(distance);
    }

    public UserDetails(Context context){
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public Context getContext() {
        return context;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public double getDiatance() {
        return diatance;
    }

    public String getDiatanceString() {
        return String.valueOf(this.diatance);
    }

    public String getUid() {
        return uid;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getBell() {
        return bell;
    }


    public String getIp() {
        return ip;
    }

    public void putJSONObject(JSONObject jsonObject){
        try {
            this.uid = String.valueOf(jsonObject.getInt("USER_ID"));
            this.name = jsonObject.getString("USER_NAME");
            this.address=jsonObject.getString("ADDRESS");
            this.mobile= jsonObject.getString("CONTACT");
            this.lat = jsonObject.getDouble("LAT");
            this.lng =jsonObject.getDouble("LNG");
            this.bell= jsonObject.getInt("BELL");
            this.imagepath =jsonObject.getString("IMAGEPATH");
            this.ip=jsonObject.getString("IP");

            Bitmap bitmap = LoadImage(JSONParser.getInstance().getIp()+"img/"+this.imagepath);
            this.image = ImageConverter.getCroppedBitmap(bitmap);
            String pat =  new DecimalFormat("#.00").format(GEOLocation.getInstance().getDistance(this.lat,this.lng));
            this.diatance =new Double(new DecimalFormat("#.00").format(GEOLocation.getInstance().getDistance(this.lat,this.lng)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Bitmap LoadImage(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

}

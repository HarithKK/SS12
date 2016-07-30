package com.knitechs.www.ss12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Koli on 7/30/2016.
 */
public class UserDetails {

    private String name;
    private Bitmap image;
    private Context context;
    private String address;
    private double diatance;
    private String mobile;

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
}

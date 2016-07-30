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
    private String mobile;

    public UserDetails(Context context,String name,int imageresouce,String mobile){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),imageresouce);
        //crop
        this.image = ImageConverter.getRoundedCornerBitmap(bitmap, 200);
        this.name = name;
        this.context = context;
        this.mobile=mobile;
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
}

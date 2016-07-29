package com.knitechs.www.ss12;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.knitechs.www.ss12.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class UserList extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        getSupportActionBar().hide();

        ImageView img = (ImageView)findViewById(R.id.imageView);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.p1);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        img.setImageBitmap(circularBitmap);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }



}

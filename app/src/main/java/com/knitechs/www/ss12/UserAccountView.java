package com.knitechs.www.ss12;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class UserAccountView extends ActionBarActivity {

    final int ANIMATION_DURATION=100;

    private int positon;
    private UserDetails userDetails;

    private TextView name;
    private TextView number;
    private TextView address;
    private TextView distance;
    private ImageView profImg;

    private Button cmdRing;

    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_view);
        getSupportActionBar().hide();

        this.name = (TextView)findViewById(R.id.txtName);
        this.number =(TextView)findViewById(R.id.txtMobile);
        this.profImg=(ImageView)findViewById(R.id.img_profile_pic);
        this.address=(TextView)findViewById(R.id.txtaddress);
        this.distance=(TextView)findViewById(R.id.txtdistance_ps);
        this.cmdRing =(Button)findViewById(R.id.cmd_ring);
        /**
         * get passed user
         */

        this.positon = getIntent().getIntExtra("UID",0);
        this.userDetails = UserList.userArrayAdaptor.getItem(this.positon);

        this.profImg.setImageBitmap(userDetails.getImage());
        this.name.setText(userDetails.getName());
        this.number.setText(userDetails.getMobile());
        this.address.setText(userDetails.getAddress());
        this.distance.setText(userDetails.getDiatanceString()+" Km away from you");

        /**
         * On touch
         * bell ring aimation
         */

        animationDrawable = new AnimationDrawable();
        animationDrawable.setOneShot(true);

        animationDrawable.addFrame(getResources().getDrawable(R.drawable.ring4),ANIMATION_DURATION);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.ring3),ANIMATION_DURATION);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.ring2),ANIMATION_DURATION);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.ring1),ANIMATION_DURATION);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.ring4),ANIMATION_DURATION);

        cmdRing.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        cmdRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cmdRing.post(animationDrawable);

                // bell ring method
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_account_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

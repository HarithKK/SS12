package com.knitechs.www.ss12;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class UserAccountView extends ActionBarActivity {

    private int positon;
    private UserDetails userDetails;

    private TextView name;
    private TextView number;
    private ImageView profImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_view);

        this.name = (TextView)findViewById(R.id.txtName);
        this.number =(TextView)findViewById(R.id.txtMobile);
        this.profImg=(ImageView)findViewById(R.id.img_profile_pic);

        /**
         * get passed user
         */

        this.positon = getIntent().getIntExtra("UID",0);
        this.userDetails = UserList.userArrayAdaptor.getItem(this.positon);

        this.profImg.setImageBitmap(userDetails.getImage());
        this.name.setText(userDetails.getName());
        this.number.setText(userDetails.getMobile());


        /**
         * On touch
         * bell ring aimation
         */


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

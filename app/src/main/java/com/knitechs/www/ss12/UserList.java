package com.knitechs.www.ss12;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.knitechs.www.ss12.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class UserList extends ActionBarActivity {

    public static UserArrayAdaptor userArrayAdaptor;
    ListView lstUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        getSupportActionBar().hide();

        userArrayAdaptor = new UserArrayAdaptor(getApplicationContext(),R.layout.user_view_box);
        userArrayAdaptor.addTempory();
        lstUsers =(ListView)findViewById(R.id.lstUsers);
        lstUsers.setAdapter(userArrayAdaptor);

        lstUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(UserList.this,UserAccountView.class);
                intent.putExtra("UID",position);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }



}

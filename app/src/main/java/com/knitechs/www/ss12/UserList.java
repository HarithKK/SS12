package com.knitechs.www.ss12;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.knitechs.www.ss12.core.JSONParser;
import com.knitechs.www.ss12.core.URLs;
import com.knitechs.www.ss12.util.SystemUiHider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class UserList extends ActionBarActivity {

    public static UserArrayAdaptor userArrayAdaptor;
    public static ListView lstUsers;
    Button cmdUpdate;
    GetUserList getUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        getSupportActionBar().hide();

        //userArrayAdaptor = new UserArrayAdaptor(getApplicationContext(),R.layout.user_view_box);
        //userArrayAdaptor.addTempory();
        lstUsers =(ListView)findViewById(R.id.lstUsers);

        new GetUserList().execute();

        lstUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(UserList.this,UserAccountView.class);
                intent.putExtra("UID",position);

                startActivity(intent);
            }
        });

        cmdUpdate = (Button) findViewById(R.id.cmdRefresh);
        cmdUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getUserList = new GetUserList();
               getUserList.execute();
            }
        });

        new TSUpdate().start();

    }

    class TSUpdate extends Thread{

        @Override
        public void run() {
            while(true){
                getUserList = new GetUserList();
                getUserList.execute();
                Log.d("TH-------","RUN");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    class GetUserList extends AsyncTask<String, String, String> {

        JSONObject obj;
        UserDetails userDetails;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //userArrayAdaptor.clear();
            //userArrayAdaptor.notifyDataSetChanged();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            int success;
            try {

                HashMap<String,String> parameters= new HashMap<>();
                parameters.put("UID",AppUser.getInstance().getUserID());
                parameters.put("LAT",String.valueOf(AppUser.getInstance().getLat()));
                parameters.put("LNG",String.valueOf(AppUser.getInstance().getLng()));
                JSONObject json = JSONParser.getInstance().makeHttpRequest(URLs.getNearByes, "GET", parameters);

                // check your log for json response
                Log.d("List Loaded", json.toString());

                success = json.getInt("success");
                UserList.userArrayAdaptor = new UserArrayAdaptor(UserList.this,R.layout.user_view_box);
                if (success == 1) {
                    JSONArray jsonArray = json.getJSONArray("recived"); // JSON Array
                    for(int i=0;i<jsonArray.length();i++){
                        obj=jsonArray.getJSONObject(i);
                        userDetails = new UserDetails(UserList.this);
                        userDetails.putJSONObject(obj);
                        UserList.userArrayAdaptor.add(userDetails);
                    }
                }else{
                }
            } catch (Exception e) {

                Log.d("-----dd----", e.getMessage());
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            try {
                UserList.lstUsers.setAdapter(userArrayAdaptor);
            } catch (Exception e) {
                Log.d("-----",e.getMessage());
            }
        }
    }


}

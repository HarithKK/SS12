package com.knitechs.www.ss12.core;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Koli on
 * 1. set sender URL
 * 2.set P dialog if needed
 * 3.set current activity conetxt is the on
 * 4.set hash map parmeters
 * 5.set success message is needed
 */
public class JSONSender extends AsyncTask<String, String, String>{

    private static String sender_url ;              // sender URL
    private ProgressDialog pDialog;                 // process dialog for when executing
    private Context currentActivity;               // current activity
    private String success_message;                 // success message when the dat inserted
    private String progress_message;
    private HashMap<String,String> parameters;      // map for data for json object
    JSONParser jsonParser = JSONParser.getInstance();       // json parser
    private static final String TAG_SUCCESS = "success";

    public JSONSender(String url, Context activity, HashMap<String, String> parameters, String success_message, String progress_message){
        this.sender_url = url;
        this.currentActivity = activity;
        this.parameters = parameters;
        this.success_message=success_message;
        this.progress_message = progress_message;
    }


    /**
         * Before starting background thread Show Progress Dialog if it is having
         * */
        @Override
        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new PDialog(currentActivity,progress_message);
//            pDialog.show();
        }

        /**
         * Do work in the save to datbse
         * */
        protected String doInBackground(String... args) {

            JSONObject json = jsonParser.makeHttpRequest(sender_url, "POST", parameters);   //  create the JSON object in parameters and send data
            Log.d("Create Response", json.toString());

            /**
             * check for success tag incomming
             */
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Data Saved"," OK");        // data saved indatabase
                    if(!success_message.isEmpty()){
                        Toast.makeText(currentActivity, success_message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Data Failed", " NO");       // Data failed
                    Toast.makeText(currentActivity,"Error",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
//                if(!pDialog.equals(null)){
//                    pDialog.dismiss();
//                }

            super.onPostExecute(file_url);

        }
}



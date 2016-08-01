package com.knitechs.www.ss12.core;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Koli on
 */
public class JSONParser {

    String charset = "UTF-8";       // character type
    HttpURLConnection conn;         // HTTP connection
    DataOutputStream wr;            // Data output stream for get out data from database
    StringBuilder result;           // reciving data string
    URL urlObj;                     // URL object for the connection
    JSONObject jObj = null;         // Json object for create Json parsers
    StringBuilder sbParams;         // string for create parameter url
    String paramsString;            // sending parameter url string
    private String ipaddress ="http://192.168.8.101/SSLS/";  //IP address of theserver

    /**
     * @param url_path    url for the servenr php service class  without the ip (folder/pages)
     * @param method port or get method
     * @param params json object parmeters
     * @return JSON object returns with include HTTP URL
     */

    private static JSONParser json_parsor;

    public static JSONParser getInstance(){
        if(json_parsor == null)
            json_parsor = new JSONParser();

        return json_parsor;
    }

    private JSONParser(){

    }

    public void setIp(String ip){
        this.ipaddress = ip;
    }
    public String getIp(){
        return this.ipaddress;
    }

    public JSONObject makeHttpRequest(String url_path, String method, HashMap<String, String> params) {

        sbParams = new StringBuilder();             // create new string for theparameters
        int i = 0;
        /**
         * create the URL parameter string from key set and values
         * key=value & key=value like that
         */
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        /**
         * create the connection URL
         */

        String url = ipaddress + url_path ;
        Log.d("PARAMETER STRING------------------",sbParams.toString());
        /**
         * create the connection object byVal method
         */

        if (method.equals("POST")) {

            /**
             * request port method
             */

            try {
                urlObj = new URL(url);                                  //  create a URL object
                conn = (HttpURLConnection) urlObj.openConnection();     //  create a HTTP connection object from theURL object
                conn.setDoOutput(true);                                 //  set the method type as Output
                conn.setRequestMethod("POST");                          //  Request method as POST
                conn.setRequestProperty("Accept-Charset", charset);     //  chrctor acceptance
                conn.setReadTimeout(10000);                             //  readable timeout for reading
                conn.setConnectTimeout(15000);                          //  connection time out for connect to server
                conn.connect();

                paramsString = sbParams.toString();                     //  parameter string

                wr = new DataOutputStream(conn.getOutputStream());      //  write the output stream to the Dtoutput stream
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();

            }

        } else if (method.equals("GET")) {

            /**
             * request get method
             */

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();   //  url ? key = val1 & key = val2
            }

            try {
                urlObj = new URL(url);                              //  create a URL object
                conn = (HttpURLConnection) urlObj.openConnection(); //  create a HTTP connection object from theURL object
                conn.setDoOutput(false);                            //  set the method type as Output
                conn.setRequestMethod("GET");                       //  Request method as GET
                conn.setRequestProperty("Accept-Charset", charset); //  chrctor acceptance
                conn.setConnectTimeout(15000);                      //  connection time out for connect to server
                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         *  response comes from server for request message this is also as a data input stream,same connection c
         */

        try {
            InputStream in = new BufferedInputStream(conn.getInputStream());        //  input strem
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  //  Buffer reader for the reader od connection statements,inpu string
            result = new StringBuilder();                                           //  respond message
            String line;
            /**
             * append the lines in to single result string
             */
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();          // disconnect the connection

        /**
         * parse the string to a json object
         */

        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;    // return json object
    }
}
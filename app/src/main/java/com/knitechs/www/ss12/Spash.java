package com.knitechs.www.ss12;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;


public class Spash extends ActionBarActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        getSupportActionBar().hide();

        progressBar =(ProgressBar)findViewById(R.id.progbar);
        progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(0);

        new PrograssComplete().start();
    }

    class PrograssComplete extends Thread{

        @Override
        public void run() {
            while(true){
                if(progressBar.getProgress()==progressBar.getMax()){

                    Intent i = new Intent(Spash.this,MainActivity.class);
                    startActivity(i);
                    finish();
                    break;
                }
                progressBar.setProgress(progressBar.getProgress()+1);
                try{
                    Thread.sleep(10);
                }catch(Exception e){

                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spash, menu);
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

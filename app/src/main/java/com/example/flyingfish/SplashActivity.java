package com.example.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //using the thread class for move the SplashActivity to another activity
        Thread thread = new Thread()
        {
            //run function for sleep the SplashActivity over the  5s
            @Override
            public void run() {
                try
                {
                    sleep(5000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        //calling for start the thread
        thread.start();
    }

    //calling the pause method

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

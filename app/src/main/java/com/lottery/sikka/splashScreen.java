package com.lottery.sikka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();


        SharedPreferences preferences=getSharedPreferences("appname",MODE_PRIVATE);
        String loginCheck=preferences.getString("loginCount","01");


        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(1000);

                    if (!loginCheck.equals("01")){
                        startActivity(new Intent(splashScreen.this,MainActivity.class));
                    }else {
                        startActivity(new Intent(splashScreen.this,create_account.class));
                    }
                    finish();
                } catch (Exception e) {
                    Log.i("d",e.getMessage());
                }
            }
        };
        background.start();



    }
}
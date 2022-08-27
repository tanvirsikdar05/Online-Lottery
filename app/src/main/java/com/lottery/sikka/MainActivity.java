package com.lottery.sikka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lottery.sikka.all_dataclass.firstLogin;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    String userKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            getSupportActionBar().hide();
        }catch (Exception e){
            Log.i("actionbar","error");
        }

        getUser();

        if (userKey == null){
            startActivity(new Intent(MainActivity.this,create_account.class));
        }


        FirebaseMessaging.getInstance().subscribeToTopic(userKey);


        int firtlogin=getIntent().getIntExtra("firstLogin",0);

        if (firtlogin == 1){
            firstTimeLogin();
        }



        //find section
        bottomNavigationView=findViewById(R.id.bottom_navigationbar_id);


        //bottom navigation declare section
        bottomNavigationView.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, new Home()).commit();
    }


    private void firstTimeLogin() {

        DatabaseReference db= FirebaseDatabase.getInstance().getReference("users").child(userKey);
        db.setValue(new firstLogin(0," "," "," "));

    }
    private void getUser() {
        SharedPreferences editor = getSharedPreferences("appname", Context.MODE_PRIVATE);
        userKey=editor.getString("loginCount","01726750916");


    }



    private NavigationBarView.OnItemSelectedListener navListener = item -> {

        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.nav_id_home:
                selectedFragment = new Home();
                break;
            case R.id.nav_id_wallet:
                selectedFragment = new wallet();
                break;
            case R.id.nav_id_histroy:
                selectedFragment = new histroy();
                break;
            case R.id.nav_id_ticket:
                selectedFragment = new buyingTicket();
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_container, selectedFragment)
                .commit();
        return true;
    };

}
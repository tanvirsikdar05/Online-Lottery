package com.lottery.sikka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lottery.sikka.LocalDb.DBhelper;
import com.lottery.sikka.all_adapter.notificationAdapter;
import com.lottery.sikka.all_adapter.requestAdapter;
import com.lottery.sikka.all_dataclass.localRequestData;
import com.lottery.sikka.all_dataclass.notificationDataClass;

import java.util.ArrayList;

public class notification_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<notificationDataClass> datalist=new ArrayList<>();
    notificationAdapter adapter;
    DBhelper dBhelper;
    TextView norecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        try {
             getSupportActionBar().setTitle("Notification");
             getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }catch (Exception e){
            Log.i("actionbar","error");
        }

        dBhelper=new DBhelper(notification_activity.this);
        norecord=findViewById(R.id.tvNonotification);
        LoadData();
        buildRecyclerview();

        if (datalist.size() <1){
            norecord.setVisibility(View.VISIBLE);
        }else norecord.setVisibility(View.GONE);
    }

    private void buildRecyclerview() {
        recyclerView=findViewById(R.id.NotificationRecyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(notification_activity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new notificationAdapter(notification_activity.this,datalist);
        recyclerView.setAdapter(adapter);
    }
    @SuppressLint({"Range", "NotifyDataSetChanged"})
    private void LoadData() {
        Cursor cursor= dBhelper.getnotification();
        datalist.clear();
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(DBhelper.NOTIFICATION_TITLE_KEY));
            String body = cursor.getString(cursor.getColumnIndex(DBhelper.NOTIFICATION_BODY_KEY));
            String date = cursor.getString(cursor.getColumnIndex(DBhelper.NOTIFICATION_DATE_KEY));
            datalist.add(new notificationDataClass(title,body,date));

        }
        cursor.close();
    }
}
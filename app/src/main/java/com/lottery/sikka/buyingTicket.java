package com.lottery.sikka;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lottery.sikka.LocalDb.DBhelper;
import com.lottery.sikka.all_adapter.localLotteryAdapter;
import com.lottery.sikka.all_dataclass.locaLotteryData;
import com.lottery.sikka.all_dataclass.localRequestData;

import java.util.ArrayList;


public class buyingTicket extends Fragment {
    RecyclerView recyclerView;
    localLotteryAdapter adapter;
    ArrayList<locaLotteryData> datalist=new ArrayList<>();
    DBhelper dBhelper;
    String userKey;
    TextView norecord;
    Dialog prograssbar;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootview=inflater.inflate(R.layout.fragment_buying_ticket, container, false);

       prograssbar=prograssDialogmet();



       norecord=rootview.findViewById(R.id.norecord);
       recyclerView=rootview.findViewById(R.id.localrecyclerveiw);
       dBhelper=new DBhelper(getContext());

       prograssbar.show();
       getUser();
       Buildrec();
       LoadfirebaseData();

       prograssbar.dismiss();
       

       
        return rootview;
    }
    public Dialog prograssDialogmet(){
        Dialog dialog=new Dialog(getContext());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.prograssdilog);

        return dialog;
    }

    private void Buildrec() {
        adapter=new localLotteryAdapter(getActivity(),datalist);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

   /* @SuppressLint("Range")
    private void LoadDataDB() {
        Cursor cursor=dBhelper.getLotteryData();
        datalist.clear();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(DBhelper.LOTTERY_NAME_KEY));
            String number = cursor.getString(cursor.getColumnIndex(DBhelper.LOTTERY_NUMBER_KEY));
            String drawdate = cursor.getString(cursor.getColumnIndex(DBhelper.DRAW_DATE_KEY));
            String buydate = cursor.getString(cursor.getColumnIndex(DBhelper.BUY_DATE_KEY));
            String status = cursor.getString(cursor.getColumnIndex(DBhelper.STATUS_KEY));
            datalist.add(new locaLotteryData(name,number,buydate,drawdate,status));

        }
        cursor.close();
    }*/
    private void getUser() {
        SharedPreferences editor = getActivity().getSharedPreferences("appname", Context.MODE_PRIVATE);
        userKey=editor.getString("loginCount","01726750916");


    }
    private void LoadfirebaseData(){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("users").child(userKey).child("lottery");
        db.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    locaLotteryData allChild=snapshot1.getValue(locaLotteryData.class);
                    datalist.add(allChild);
                }
                adapter.notifyDataSetChanged();
                if (datalist.size() <1){
                    norecord.setVisibility(View.VISIBLE);
                }else norecord.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
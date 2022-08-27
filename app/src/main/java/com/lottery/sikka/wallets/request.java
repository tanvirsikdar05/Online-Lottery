package com.lottery.sikka.wallets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.lottery.sikka.R;
import com.lottery.sikka.all_adapter.requestAdapter;
import com.lottery.sikka.all_dataclass.localRequestData;
import com.lottery.sikka.all_dataclass.twoplayerData;

import java.util.ArrayList;


public class request extends Fragment {
    RecyclerView recyclerView;
    requestAdapter adapter;
    ArrayList<localRequestData> datalist=new ArrayList<>();
    DBhelper dBhelper;
    TextView nofound;
    String userKey;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootview=inflater.inflate(R.layout.fragment_request, container, false);

       nofound=rootview.findViewById(R.id.tvnofound);
       recyclerView=rootview.findViewById(R.id.recyclerviewid);
       getUser();
       dBhelper=new DBhelper(getContext());

       loadDatafirebase();
       buildRecyclerview();



        return rootview;
    }
    private void getUser() {
        SharedPreferences editor = getActivity().getSharedPreferences("appname", Context.MODE_PRIVATE);
        userKey=editor.getString("loginCount","01726750916");


    }

    private void buildRecyclerview() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter=new requestAdapter(getActivity(),datalist);
        recyclerView.setAdapter(adapter);
    }

    /*@SuppressLint({"Range", "NotifyDataSetChanged"})
    private void LoadData() {
        Cursor cursor= dBhelper.getPaymentData();
        datalist.clear();
        while (cursor.moveToNext()){
            String number = cursor.getString(cursor.getColumnIndex(DBhelper.PAYMENT_TRX_KEY));
            String date = cursor.getString(cursor.getColumnIndex(DBhelper.PAYMENT_DATE_KEY));
            String bankname = cursor.getString(cursor.getColumnIndex(DBhelper.PAYMENT_BANKNAME_KEY));
            String status = cursor.getString(cursor.getColumnIndex(DBhelper.PAYMENT_STATUS_KEY));
            String type = cursor.getString(cursor.getColumnIndex(DBhelper.PAYMENT_TYPE_KEY));
            String price = cursor.getString(cursor.getColumnIndex(DBhelper.PAYMENT_PRICE_KEY));

            datalist.add(new localRequestData(number,date,bankname,status,type,price));

        }
        cursor.close();
    }*/
    private void loadDatafirebase(){
        DatabaseReference db=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("request");
        db.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    localRequestData allChild=snapshot1.getValue(localRequestData.class);
                    datalist.add(allChild);
                }
                adapter.notifyDataSetChanged();
                if (datalist.size() <1){
                    nofound.setVisibility(View.VISIBLE);
                }else nofound.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
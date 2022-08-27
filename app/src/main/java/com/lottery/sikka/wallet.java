package com.lottery.sikka;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lottery.sikka.all_adapter.vpAdapter;
import com.lottery.sikka.wallets.deposite;
import com.lottery.sikka.wallets.withdraw;

public class wallet extends Fragment {
    TextView blance;
    String userKey;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    vpAdapter adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root_view=inflater.inflate(R.layout.wallet, container, false);





        blance=root_view.findViewById(R.id.tvblance);
       tabLayout=root_view.findViewById(R.id.tablayoutid);
       viewPager2=root_view.findViewById(R.id.viewpager2);
       getUser();





       getFirebaseBlance();

        FragmentManager fm= getActivity().getSupportFragmentManager();
        adapter=new vpAdapter(fm,getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Deposit"));
        tabLayout.addTab(tabLayout.newTab().setText("Withdraw"));
        tabLayout.addTab(tabLayout.newTab().setText("Transaction"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        return root_view;
    }



    private void getUser() {
        SharedPreferences editor = getActivity().getSharedPreferences("appname", Context.MODE_PRIVATE);
        userKey=editor.getString("loginCount","01726750916");


    }

    private void getFirebaseBlance() {
        DatabaseReference blancemref= FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
        blancemref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int blancel=snapshot.getValue(Integer.class);
                blance.setText(String.valueOf(blancel));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                c_toast(error.getMessage());
            }
        });
    }

    private void c_toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}
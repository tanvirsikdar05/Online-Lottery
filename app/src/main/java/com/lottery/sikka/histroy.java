package com.lottery.sikka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class histroy extends Fragment {

    LinearLayout layout,facebook,whatapp,instagram,twiter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootview=inflater.inflate(R.layout.fragment_histroy, container, false);


       layout=rootview.findViewById(R.id.layoutLogout);
       facebook=rootview.findViewById(R.id.facebookid);
       whatapp=rootview.findViewById(R.id.whatsapp);
       instagram=rootview.findViewById(R.id.instagram);
       twiter=rootview.findViewById(R.id.twitter);

       layout.setOnClickListener(view -> {
           SharedPreferences.Editor editor = getActivity().getSharedPreferences("appname", Context.MODE_PRIVATE).edit();
           editor.putString("loginCount", "01");
           editor.apply();
           Intent intent=new Intent(getActivity(),create_account.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);

       });
       facebook.setOnClickListener(view -> {
           Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/groups/747054593183979"));
           startActivity(browserIntent);
       });
       whatapp.setOnClickListener(view -> {
           String url = "https://api.whatsapp.com/send?phone="+"01608736516";
           Intent i = new Intent(Intent.ACTION_VIEW);
           i.setData(Uri.parse(url));
           startActivity(i);
       });
       instagram.setOnClickListener(view -> {
           Toast.makeText(getActivity(), "Not available ", Toast.LENGTH_SHORT).show();
       });
        twiter.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Not available ", Toast.LENGTH_SHORT).show();
        });


        return rootview;
    }

}
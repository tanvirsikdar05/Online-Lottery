package com.lottery.sikka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class winner_page extends AppCompatActivity {
    ImageView winner_image;
    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_page);

        try {
           getSupportActionBar().setTitle("Winner");
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            Log.i("actionbar","error");
        }

        winner_image=findViewById(R.id.tv_img);
        details=findViewById(R.id.tv_details);

        String url=getIntent().getStringExtra("url");
        String doc=getIntent().getStringExtra("doc");

        details.setText(doc);
        LoadWinnerImage(url);
    }

    private void LoadWinnerImage(String url) {
        Picasso.get().load(url).into(winner_image);
    }
    private void ctoast(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }
}
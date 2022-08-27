package com.lottery.sikka;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.lottery.sikka.all_dataclass.winners_image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class viewpager_adapter extends RecyclerView.Adapter<viewpager_adapter.ViewHolder> {
    private Context ctx;
    ArrayList<winners_image> items;
    ViewPager2 viewPager2;

    viewpager_adapter(Context ctx,ViewPager2 viewPager2,ArrayList<winners_image> items){
        this.ctx=ctx;
        this.viewPager2=viewPager2;
        this.items=items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ctx).inflate(R.layout.viewpager_imageholder,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       //holder.setImage(items.get(position));

        String url=items.get(position).getUrl();
        String doc=items.get(position).getDoc();
      Picasso.get().load(url)
              .into(holder.imageView);

        if (position == items.size()- 2){
            viewPager2.post(runnable);
        }

        holder.imageView.setOnClickListener(view -> {

            Intent n=new Intent(ctx,winner_page.class);
            n.putExtra("doc",doc);
            n.putExtra("url",url);
            ctx.startActivity(n);
        });


    }
    private final Runnable runnable=new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            items.addAll(items);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        GifImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview_holder);

        }
       /*void setImage(slideitems sliderItems){

            imageView.setImageResource(sliderItems.getImg());

        }*/
    }
}

package com.lottery.sikka.all_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lottery.sikka.R;
import com.lottery.sikka.all_dataclass.lottery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class lottery_adapter extends RecyclerView.Adapter<lottery_adapter.ViewHolder> {
    Context context;
    ArrayList<lottery> alldata;
    private lotteryItemclick lotteryItemclick;


    public lottery_adapter(Context context,ArrayList<lottery> alldata){
        this.context=context;
        this.alldata=alldata; }
    public void onitemclicklistener(lotteryItemclick click){
        this.lotteryItemclick=click;
    }

    public interface lotteryItemclick{
        void onclick(int possition);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lottery_view,parent,false);
        return new ViewHolder(view,lotteryItemclick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.imageView.setImageResource(allimages[position]);
        String url=alldata.get(position).getImg();
        Picasso.get().load(url)

                .into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return alldata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView buy;

        public ViewHolder(@NonNull View itemView,lotteryItemclick click) {
            super(itemView);
            imageView=itemView.findViewById(R.id.lottery_view_imageview);
            buy=itemView.findViewById(R.id.lottery_buy);

            buy.setOnClickListener(view -> click.onclick(getAdapterPosition()));

        }
    }
}

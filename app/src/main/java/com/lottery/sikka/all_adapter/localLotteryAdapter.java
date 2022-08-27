package com.lottery.sikka.all_adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lottery.sikka.R;
import com.lottery.sikka.all_dataclass.locaLotteryData;
import com.lottery.sikka.all_dataclass.lottery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class localLotteryAdapter extends RecyclerView.Adapter<localLotteryAdapter.ViewHolder> {
    Context context;
    ArrayList<locaLotteryData> datalist;




    public localLotteryAdapter(Context context,ArrayList<locaLotteryData> datalist){
        this.context=context;
        this.datalist=datalist;

    }



    @NonNull
    @Override
    public localLotteryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.buyinglottery_exview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull localLotteryAdapter.ViewHolder holder, int position) {
        //holder.imageView.setImageResource(allimages[position]);
        holder.name.setText(datalist.get(position).getLotteryName());
        holder.number.setText(datalist.get(position).getLotteryNumber());
        holder.drawdate.setText(datalist.get(position).getDrawDate());
        holder.buydate.setText(datalist.get(position).getBuyDate());
        holder.status.setText(datalist.get(position).getStatus());


        holder.status.setTextColor(getColor(position));


    }
    private int getColor(int position) {
        String status = datalist.get(position).getStatus();
        if(status.equals("lose"))
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context,R.color.simple_red)));
        else if(status.equals("win"))
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.green)));

        return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white_three)));
    }

    @Override
    public int getItemCount() {
       return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView name,number,buydate,drawdate,status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          name=itemView.findViewById(R.id.tvname);
          number=itemView.findViewById(R.id.tvnumber);
          buydate=itemView.findViewById(R.id.tvbuyingdate);
          drawdate=itemView.findViewById(R.id.tvdrawdate);
          status=itemView.findViewById(R.id.tvstatus);



        }
    }
}

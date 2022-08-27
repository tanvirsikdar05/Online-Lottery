package com.lottery.sikka.all_adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lottery.sikka.R;
import com.lottery.sikka.all_dataclass.localRequestData;
import com.lottery.sikka.all_dataclass.notificationDataClass;

import java.util.ArrayList;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ViewHolder> {

    Context context;
    ArrayList<notificationDataClass> datalist;

    public notificationAdapter(Context context,ArrayList<notificationDataClass> datalist){
        this.context=context;
        this.datalist=datalist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notification_recyclerview_ex,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(datalist.get(position).getNotificationTitle());
        holder.date.setText(datalist.get(position).getNotificationTime());
        holder.body.setText(datalist.get(position).getNotificationBody());


    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,body,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tvNotificationTitle);
            body=itemView.findViewById(R.id.tvNotificatioDetails);
            date=itemView.findViewById(R.id.tvNotificationTime);

        }
    }
}


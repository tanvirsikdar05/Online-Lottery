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

import java.util.ArrayList;

public class requestAdapter extends RecyclerView.Adapter<requestAdapter.ViewHolder> {

    Context context;
    ArrayList<localRequestData> datalist;

    public requestAdapter(Context context,ArrayList<localRequestData> datalist){
        this.context=context;
        this.datalist=datalist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.payment_ex,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.number.setText(datalist.get(position).getTrx());
        holder.date.setText(datalist.get(position).getDate());
        holder.bankname.setText(datalist.get(position).getBankname());
        holder.status.setText(datalist.get(position).getStatus());
        holder.type.setText(datalist.get(position).getType());
        holder.price.setText(datalist.get(position).getTk());

        holder.status.setTextColor(getColor(position));

    }
    private int getColor(int position) {
        String status = datalist.get(position).getStatus();
        if(status.equals("rejected"))
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context,R.color.simple_red)));
        else if(status.equals("successful"))
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.green)));

        return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(context, R.color.white_three)));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number,date,bankname,status,type,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number=itemView.findViewById(R.id.tvnumber);
            date=itemView.findViewById(R.id.tvdate);
            bankname=itemView.findViewById(R.id.tvbankname);
            status=itemView.findViewById(R.id.tvstatus);
            type=itemView.findViewById(R.id.tvtype);
            price=itemView.findViewById(R.id.tvprice);
        }
    }
}

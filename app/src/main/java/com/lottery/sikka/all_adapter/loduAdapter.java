package com.lottery.sikka.all_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottery.sikka.R;
import com.lottery.sikka.all_dataclass.twoplayerData;

import java.util.ArrayList;

public class loduAdapter extends RecyclerView.Adapter<loduAdapter.Viewholder> {



    Context context;
    ArrayList<twoplayerData> datalst;
    public itemclick itemclick;

    public void onClicklistener(itemclick itemclick){
        this.itemclick=itemclick;
    }

    public interface itemclick{
        void onClick(int possition);
    }

    public loduAdapter(Context context,ArrayList<twoplayerData> datalst){
        this.context=context;
        this.datalst=datalst;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lodu,parent,false);
        return new Viewholder(view,itemclick);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.loduName.setText(datalst.get(position).getName());
        holder.LoduDate.setText(datalst.get(position).getDate());
        holder.prize.setText(datalst.get(position).getPrize());
        holder.entryfee.setText(String.valueOf(datalst.get(position).getFee()));
        holder.joiningtype.setText(datalst.get(position).getType());
        holder.bordtype.setText(datalst.get(position).getBoard());
        holder.remaintime.setText(datalst.get(position).getDate());
        holder.progressBar.setMax(2);

        String playerOne,playerTwo,winner;
        playerOne=datalst.get(position).getPlayerone();
        playerTwo=datalst.get(position).getPlayertwo();
        winner=datalst.get(position).getWinner();

        if (playerOne.isEmpty() && playerTwo.isEmpty()){
            holder.emptyspot.setText("2 spot is left");
            holder.joinBtn.setText("Join");
        }else if (playerOne.isEmpty() || playerTwo.isEmpty()){
            holder.emptyspot.setText("1/2 spot is left");
            holder.progressBar.setProgress(1);
            holder.joinBtn.setText("Join");
        }else {
            holder.emptyspot.setText("0 spot is left");
            holder.progressBar.setProgress(2);
            holder.joinBtn.setText("Room code");
        }

        if (!winner.isEmpty()){
            holder.joinBtn.setText("winner");
        }



    }

    @Override
    public int getItemCount() {
        return datalst.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView loduName,LoduDate,prize,entryfee,joiningtype,bordtype,remaintime,emptyspot,joinBtn;
        ProgressBar progressBar;

        public Viewholder(@NonNull View itemView,itemclick itemclick1) {
            super(itemView);

            loduName=itemView.findViewById(R.id.tv_loduname);
            LoduDate=itemView.findViewById(R.id.tv_lodudate);
            prize=itemView.findViewById(R.id.tv_prize);
            entryfee=itemView.findViewById(R.id.tv_entryfee);
            joiningtype=itemView.findViewById(R.id.tv_joiningtypre);
            bordtype=itemView.findViewById(R.id.tv_bordtype);
            remaintime=itemView.findViewById(R.id.tv_remaintime);
            emptyspot=itemView.findViewById(R.id.tv_emptyspot);
            progressBar=itemView.findViewById(R.id.prograssbar);
            joinBtn=itemView.findViewById(R.id.tv_joinButton);
            joinBtn.setOnClickListener(view -> {
                itemclick1.onClick(getAdapterPosition());
            });
        }


    }
}

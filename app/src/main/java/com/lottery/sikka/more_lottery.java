package com.lottery.sikka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lottery.sikka.LocalDb.DBhelper;
import com.lottery.sikka.all_adapter.lottery_adapter;
import com.lottery.sikka.all_dataclass.locaLotteryData;
import com.lottery.sikka.all_dataclass.lottery;
import com.lottery.sikka.wallets.deposite;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class more_lottery extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<lottery> lottery_data=new ArrayList<>();
    lottery_adapter adapter;
    String userKey;
    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._more_lottery);

        try {
           getSupportActionBar().setTitle("All lottery");
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }catch (Exception e){
            Log.i("actionbar","error");
        }

        recyclerView=findViewById(R.id.all_lot_resid);


        getUser();

        dBhelper=new DBhelper(this);
        loadLotteryData();
        build_recyclerview();

        adapter.onitemclicklistener(possition -> {
            BuyLottery(possition);
        });
    }
    private void getUser() {
        SharedPreferences editor = getSharedPreferences("appname", Context.MODE_PRIVATE);
        userKey=editor.getString("loginCount","01726750916");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void build_recyclerview() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new lottery_adapter(this,lottery_data);
        recyclerView.setAdapter(adapter);
    }
    private void loadLotteryData() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("admin").child("all_lottery");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lottery_data.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    lottery lotData=dataSnapshot.getValue(lottery.class);
                    lottery_data.add(new lottery(lotData.getId(),lotData.getDetails(),lotData.getImg(),lotData.getPrice(),
                            lotData.getName(),lotData.getDrawDate()));

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                c_toast(error.getMessage());

            }
        });
    }

    private void c_toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void BuyLottery(int possition) {

        Dialog lodingDialog=new Dialog(this);
        lodingDialog.setContentView(R.layout.loding_dialog);
        lodingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lodingDialog.setCancelable(true);
        lodingDialog.show();

        DatabaseReference blancemref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
        blancemref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int blance=snapshot.getValue(Integer.class);
                if (blance >= lottery_data.get(possition).getPrice()){
                    LotteryConfirmDialog(possition);
                }else {
                    LowBlanceDialog();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                c_toast(error.getMessage());
            }
        });
        lodingDialog.dismiss();


    }

    public void LowBlanceDialog() {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.addblance_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView confirm=dialog.findViewById(R.id.tv_add_blance);
        TextView cancel=dialog.findViewById(R.id.tvcancel);

        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        confirm.setOnClickListener(view -> {
            dialog.dismiss();
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.nav_container,new deposite());
            fr.commit();
        });

        dialog.show();
    }

    public void LotteryConfirmDialog(int possition) {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.lotteryconfirmdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView cnfrm_btn=dialog.findViewById(R.id.tv_lotteryconfirm);
        TextView cancel=dialog.findViewById(R.id.tvcancel);

        cnfrm_btn.setOnClickListener(view -> {
            dialog.dismiss();
            minusBlance(possition);

        });
        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();

    }

    public void showSuccessDialog(String LotteryNumber) {
        Dialog lodingDialog=new Dialog(this);
        lodingDialog.setContentView(R.layout.success_dialog);
        lodingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lodingDialog.setCancelable(true);

        TextView number=lodingDialog.findViewById(R.id.tv_lotterynumber);
        TextView okbtn=lodingDialog.findViewById(R.id.btn_ok);
        String  decodednumber="Number: "+LotteryNumber;
        number.setText(decodednumber);
        okbtn.setOnClickListener(view -> lodingDialog.dismiss());
        lodingDialog.show();
    }

    private void minusBlance(int possition) {
        DatabaseReference blancemref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
        blancemref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int firebaseBal=snapshot.getValue(Integer.class);
                int lotteryPrice=lottery_data.get(possition).getPrice();
                int nowBlance=firebaseBal - lotteryPrice;
                uploadBlanceFirebase(nowBlance,possition);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                c_toast(error.getMessage());
            }
        });


    }

    public void uploadBlanceFirebase(int nowBlance,int possition) {
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
        mref.setValue(nowBlance).addOnSuccessListener(unused -> {
            genarateLotteryNumber(possition);
        });
    }


    public void genarateLotteryNumber(int possition) {
        String looteryId=lottery_data.get(possition).getId();
        String lotteryName=lottery_data.get(possition).getName() +"("+lottery_data.get(possition).getPrice()+")";
        String currentDate = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault()).format(new Date());
        String drawdatedec="Draw: "+lottery_data.get(possition).getDrawDate();

        long dbId=dBhelper.add_buying_lottery(lotteryName,userKey,currentDate,drawdatedec,"pending");
        String LotteryNumber=looteryId + userKey + dbId;

        dBhelper.update_lottery(String.valueOf(dbId),LotteryNumber);
        //upload lottery data user section
        DatabaseReference upref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("lottery").push();
        upref.setValue(new locaLotteryData(lotteryName,LotteryNumber,currentDate,
                lottery_data.get(possition).getDrawDate(),"Pending"));

        //upload lottery data global section
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("admin").child("drawlottery").child(looteryId).push();
        mref.setValue(LotteryNumber).addOnSuccessListener(unused -> showSuccessDialog(LotteryNumber));



    }
}
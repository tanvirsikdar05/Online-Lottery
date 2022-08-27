package com.lottery.sikka.wallets;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lottery.sikka.LocalDb.DBhelper;
import com.lottery.sikka.R;
import com.lottery.sikka.all_dataclass.depositeRequestData;
import com.lottery.sikka.all_dataclass.getDepositeData;
import com.lottery.sikka.all_dataclass.getwithdrawData;
import com.lottery.sikka.all_dataclass.localRequestData;
import com.lottery.sikka.all_dataclass.withdrawRequestData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class withdraw extends Fragment {
    CardView nogod,bkash,rocket,upay;
    String userKey;
    DBhelper dBhelper;

    Dialog prograssbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root_view=inflater.inflate(R.layout.fragment_withdraw, container, false);
       nogod=root_view.findViewById(R.id.crd_nogod);
       bkash=root_view.findViewById(R.id.crd_bkash);
       rocket=root_view.findViewById(R.id.crd_rocket);
       upay=root_view.findViewById(R.id.crd_upay);

       prograssbar=prograssDialogmet();


       getUser();
       dBhelper=new DBhelper(getContext());


       nogod.setOnClickListener(view -> {
           getDataFromFirebase("nogod");

       });
        bkash.setOnClickListener(view -> {
            getDataFromFirebase("bkash");
        });
        rocket.setOnClickListener(view -> {
            getDataFromFirebase("rocket");
        });
        upay.setOnClickListener(view -> {
           getDataFromFirebase("upay");
        });
        return root_view;
    }
    private void getUser() {
        SharedPreferences editor = getActivity().getSharedPreferences("appname", Context.MODE_PRIVATE);
        userKey=editor.getString("loginCount","01726750916");


    }

    private void getDataFromFirebase(String bankName) {
        prograssbar.show();
        DatabaseReference mref= FirebaseDatabase.getInstance().getReference("admin").child("withdrawInfo").child(bankName);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getwithdrawData mm=snapshot.getValue(getwithdrawData.class);
                openDialog(String.valueOf(mm.getMinBlance()),bankName,mm.getMsg());


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

    private void openDialog(String min_amount,String bank_name,String _massage) {
        Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.withdraw_dialog);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView minimum_amount,wallet_number_msg,massage,cofirm;
        EditText amount,number;
        ImageView bank_logo;


        minimum_amount =dialog.findViewById(R.id.tv_min_msg);
        cofirm =dialog.findViewById(R.id.tv_conform);
        wallet_number_msg =dialog.findViewById(R.id.tv_number_msg);
        massage =dialog.findViewById(R.id.tv_msg);


        amount=dialog.findViewById(R.id.edtxt_amount);
        number=dialog.findViewById(R.id.edtxt_wallet_number);
        bank_logo=dialog.findViewById(R.id.bank_logo);

        String _min_amount="Amount (min "+min_amount+" BDT): ";
        String _wallet_number_msg="আপনার "+bank_name+" ওয়ালেট নাম্বার : ";


        minimum_amount.setText(_min_amount);
        wallet_number_msg.setText(_wallet_number_msg);
        massage.setText(_massage);
        amount.setHint(min_amount);

        prograssbar.dismiss();

        switch (bank_name) {
            case "nogod":
                bank_logo.setImageResource(R.drawable.nogo_logo);
                break;
            case "bkash":
                bank_logo.setImageResource(R.drawable.bkash_logo);
                break;
            case "rocket":
                bank_logo.setImageResource(R.drawable.rocket_logo);
                break;
            case "upay":
                bank_logo.setImageResource(R.drawable.upay_logo);
                break;
        }

        cofirm.setOnClickListener(view -> {

            DatabaseReference blancemref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
            blancemref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int blance=snapshot.getValue(Integer.class);

                    if (amount.getText().toString().isEmpty() || number.getText().toString().isEmpty()) {
                        c_toast("fill curretly");
                    }else {
                        if (blance <= Integer.parseInt(amount.getText().toString())){
                            amount.setError("blance high");
                        }else if (Integer.parseInt(min_amount) > Integer.parseInt(amount.getText().toString())){
                            amount.setError("blance low");
                        }else {
                            dialog.dismiss();
                            prograssbar.show();
                            uploadFirebase(amount.getText().toString(),number.getText().toString(), bank_name);

                        }

                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    c_toast(error.getMessage());
                }
            });


        });


        dialog.show();
    }

    private void uploadFirebase(String amount, String number, String bank_name) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("admin").child("withdrawRequest")
                .push();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("request").push();

        String currentDate = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String decodedDate=currentDate+","+currentTime;

        reference.setValue(new localRequestData(number,decodedDate,bank_name,"requested","Withdraw",amount));
        /*.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dBhelper.addPaymentData(number,decodedDate,bank_name,"requested","Withdraw",
                        amount);
            }
        });*/

        ref.setValue(new withdrawRequestData(amount,number,
                bank_name,decodedDate,userKey)).addOnSuccessListener(unused -> {
                    prograssbar.dismiss();
                    open_success_dialog();
        });
    }
    public Dialog prograssDialogmet(){
        Dialog dialog=new Dialog(getContext());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.prograssdilog);

        return dialog;
    }

    private void open_success_dialog() {
        Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.simple_success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView success_msg,ok_button;

        success_msg=dialog.findViewById(R.id.tvmassage);
        success_msg.setText("request send successfully");
        ok_button=dialog.findViewById(R.id.btn_ok);

        ok_button.setOnClickListener(view -> {
            dialog.dismiss();
        });


        dialog.show();
    }
}
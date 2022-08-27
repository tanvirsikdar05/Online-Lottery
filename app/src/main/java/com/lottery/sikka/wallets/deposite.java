package com.lottery.sikka.wallets;



import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lottery.sikka.LocalDb.DBhelper;
import com.lottery.sikka.R;
import com.lottery.sikka.all_dataclass.depositeRequestData;
import com.lottery.sikka.all_dataclass.getDepositeData;
import com.lottery.sikka.all_dataclass.localRequestData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class deposite extends Fragment {
    CardView nogod,bkash,rocket,upay;
    String userKey;
    DBhelper dBhelper;
    AlertDialog.Builder builder;
    Dialog progressDialog;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root_view=inflater.inflate(R.layout.fragment_deposite, container, false);
       nogod=root_view.findViewById(R.id.crd_nogod);
       bkash=root_view.findViewById(R.id.crd_bkash);
       rocket=root_view.findViewById(R.id.crd_rocket);
       upay=root_view.findViewById(R.id.crd_upay);

       progressDialog=prograssDialogm();

        /*progressDialog = getDialogProgressBar().create();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/





       getUser();
       dBhelper=new DBhelper(getActivity());

       // getDataFromDirebase("nogod");




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
   /* public AlertDialog.Builder getDialogProgressBar() {

        if (builder == null) {
            builder = new AlertDialog.Builder(getContext());


            builder.setCancelable(true);
            builder.setView(R.layout.prograssdilog);

           *//* final ProgressBar progressBar = new ProgressBar(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            progressBar.setLayoutParams(lp);*//*
        }
        return builder;
    }*/

    public Dialog prograssDialogm(){
        Dialog dialog=new Dialog(getContext());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.prograssdilog);

        return dialog;
    }



    private void getDataFromFirebase(String bankName) {

        progressDialog.show();
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("admin").child("depositeInfo").child(bankName);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    getDepositeData mm=snapshot.getValue(getDepositeData.class);

                    openDialog(bankName,mm.getBankNUmber(),String.valueOf(mm.getMin()), mm.getMsg(),
                            mm.getStatus());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                c_toast(error.getMessage());

            }
        });
    }

    private void openDialog(String bank_name,String _agent_number,String min_amount,String msg,String status) {
        if (status.toLowerCase(Locale.ROOT).equals("on")){
            Dialog dialog=new Dialog(getActivity());
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.deposite_dialog);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView minimum_amount,wallet_number_msg,massage,agent_number,how_to_deposite,confirm_btn,_bank_name,agen_number_msg;
            EditText amount,number,trns_number;
            ImageView bank_logo;


            minimum_amount =dialog.findViewById(R.id.tv_minimum_ammount);
            agen_number_msg =dialog.findViewById(R.id.tv_agent_number_msg);
            _bank_name =dialog.findViewById(R.id.tv_bank_name);
            wallet_number_msg =dialog.findViewById(R.id.tv_wallet_number_msg);
            massage =dialog.findViewById(R.id.tv_massage);
            agent_number =dialog.findViewById(R.id.tv_agent_number);
            confirm_btn =dialog.findViewById(R.id.tv_conform);

            amount=dialog.findViewById(R.id.edtxt_amount);
            trns_number=dialog.findViewById(R.id.edtxt_transition_number);
            number=dialog.findViewById(R.id.edtxt_number);

            bank_logo=dialog.findViewById(R.id.img_bank_logo);

            String _min_amount="Amount (min "+min_amount+" BDT): ";
            String _wallet_number_msg="আপনার "+bank_name+" ওয়ালেট নাম্বার (cash out ): ";
            String msg_bank_name="Bank Name: "+bank_name;
            String _agent_number_msg=bank_name+" Wallet Number : ";

            minimum_amount.setText(_min_amount);
            wallet_number_msg.setText(_wallet_number_msg);
            massage.setText(msg);
            agen_number_msg.setText(_agent_number_msg);
            agent_number.setText(_agent_number);
            _bank_name.setText(msg_bank_name);
            amount.setHint(min_amount);
            progressDialog.dismiss();




            agent_number.setOnClickListener(view -> { copy_to_clipbord(agent_number.getText().toString()); });

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

            confirm_btn.setOnClickListener(view -> {

                String _amount=amount.getText().toString();
                String _trns_number=trns_number.getText().toString();
                String _number=number.getText().toString();
                if (_amount.isEmpty() || _trns_number.isEmpty() || _number.isEmpty()){
                    c_toast("Fill data currectly");
                }else {
                    if (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(min_amount)){
                        amount.setError("Blance is low");
                    }else {
                        progressDialog.show();
                        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("admin").child("depositeRequest")
                                .push();
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("request").push();

                        String currentDate = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                        String decodedDate=currentDate+","+currentTime;

                        reference.setValue(new localRequestData(_trns_number,decodedDate,bank_name,"requested","Add money",_amount));
                        /*.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dBhelper.addPaymentData(_number,decodedDate,bank_name,"requested","Add money",
                                        _amount);
                            }
                        });*/

                        mref.setValue(new depositeRequestData(_amount,_number,_trns_number,bank_name,userKey)).addOnSuccessListener(unused -> {
                            dialog.dismiss();
                           progressDialog.dismiss();
                            open_success_dialog();
                        });
                    }



                }

            });


            dialog.show();
        }else {
            c_toast(bank_name+" "+"not available at this moment");
        }

    }




    private void copy_to_clipbord(String agent_nmbr) {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", agent_nmbr);
        clipboard.setPrimaryClip(clip);
        c_toast("Number Copied");

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
    public void c_toast(String txt){
        Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
    }
}
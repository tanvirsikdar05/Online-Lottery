package com.lottery.sikka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lottery.sikka.all_adapter.loduAdapter;
import com.lottery.sikka.all_dataclass.twoplayerData;

import java.util.ArrayList;

public class lodu_actvity extends AppCompatActivity {
    ArrayList<twoplayerData> datalist=new ArrayList<>();
    RecyclerView loduRecyclerView;
    loduAdapter adapter;
    String userKey;

    TextView norecord;
    Dialog prograssbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lodu_actvity);

        prograssbar=prograssDialogmet();

        try {
            getSupportActionBar().setTitle("Lodu");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            Log.i("actionbar","error");
        }

        loduRecyclerView=findViewById(R.id.lodurecview);
        norecord=findViewById(R.id.norecordd);

        prograssbar.show();
        getUser();
        Two_player();
        BuildRecView();

        prograssbar.dismiss();





        adapter.onClicklistener(possition -> {
            whenClick(possition);


        });
    }
    public Dialog prograssDialogmet(){
        Dialog dialog=new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.prograssdilog);

        return dialog;
    }
    private void getUser() {
        SharedPreferences editor = getSharedPreferences("appname", Context.MODE_PRIVATE);
        userKey=editor.getString("loginCount","01726750916");


    }

    private void whenClick(int possition) {

        String playerOne,playerTwo,winner;
        playerOne=datalist.get(possition).getPlayerone();
        playerTwo=datalist.get(possition).getPlayertwo();
        winner=datalist.get(possition).getWinner();

        if (playerOne.isEmpty() && playerTwo.isEmpty()){
            joinDialog(1,possition);
           
        }else if (playerOne.isEmpty() || playerTwo.isEmpty()){
            joinDialog(2,possition);
        }else if (!winner.isEmpty()){
            winnerDialog(possition);
        } else {
            roomCodeDialog(playerOne,playerTwo,possition);
        }


    }

    @SuppressLint("SetTextI18n")
    private void winnerDialog(int position) {

        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.winner_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();

        TextView okbtn,winnermsg;
        okbtn=dialog.findViewById(R.id.tvok);
        winnermsg=dialog.findViewById(R.id.tvwinnermsg);

        okbtn.setOnClickListener(view -> dialog.dismiss());

        winnermsg.setText("Winner: "+datalist.get(position).getWinner());
    }

    private void roomCodeDialog(String playerone,String playertwo,int position) {
        String[] plone=playerone.split("/");
        String[] pltwo=playertwo.split("/");

        if (plone[0].equals(userKey) || pltwo[0].equals(userKey)){
            showRoomDialog(position);

        }else {
            c_toast("You are not joining this match");
        }
    }

    private void showRoomDialog(int position) {

        Dialog dialog=new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.roomcodedialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView roomcode,button,msg;

        roomcode=dialog.findViewById(R.id.tv_roomcode);
        button=dialog.findViewById(R.id.tvok);
        msg=dialog.findViewById(R.id.loduprice);

        button.setOnClickListener(view -> dialog.dismiss());

        if (!datalist.get(position).getRoomcode().isEmpty()){
            roomcode.setVisibility(View.VISIBLE);
            roomcode.setText(datalist.get(position).getRoomcode());

        }else {
            roomcode.setVisibility(View.GONE);
            msg.setText(R.string.waitmsg);
        }

        roomcode.setOnClickListener(view -> {
            ClipboardManager clipboardManager= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData=ClipData.newPlainText("0",roomcode.getText().toString());
            clipboardManager.setPrimaryClip(clipData);
            c_toast("copied");
        });

    }

    private void joinDialog(int status,int position) {

        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.lodujoindialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();

        TextView currentbl,loduprice,cbutton;
        EditText loduName;


        cbutton=dialog.findViewById(R.id.btnconfrm);
        loduName=dialog.findViewById(R.id.edt_loduusername);
        loduprice=dialog.findViewById(R.id.loduprice);

        loduprice.setText("Entry Fee: "+ datalist.get(position).getFee());



        cbutton.setOnClickListener(view -> {

            if (!loduName.getText().toString().isEmpty()){

                DatabaseReference blancemref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
                blancemref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int blance=snapshot.getValue(Integer.class);
                        if (blance >= datalist.get(position).getFee()){
                            buyLodu(position,status,loduName.getText().toString());
                            dialog.dismiss();

                        }else {
                            c_toast("Blance is low ");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        c_toast(error.getMessage());
                    }
                });

            }else {
                c_toast("Enter Lodu user Name");
            }



        });



    }

    private void buyLodu(int position,int status,String massage) {
        String playerInfo=userKey+"/"+massage;

        if (status == 2){
            //only one spot empty
            if (datalist.get(position).getPlayerone().isEmpty()){
                //add to player one
                DatabaseReference mref=FirebaseDatabase.getInstance().getReference("lodutwoplayer").child(datalist.get(position).getChildkey()).child("playerone");

                mref.setValue(playerInfo).addOnSuccessListener(unused -> minusBlance(position));


            }else if (datalist.get(position).getPlayertwo().isEmpty()){
                // add to playertwo

                DatabaseReference mref2=FirebaseDatabase.getInstance().getReference("lodutwoplayer").child(datalist.get(position).getChildkey()).child("playertwo");
                mref2.setValue(playerInfo).addOnSuccessListener(unused -> minusBlance(position));
            }
        }else {
            /// any spot

            DatabaseReference mref=FirebaseDatabase.getInstance().getReference("lodutwoplayer").child(datalist.get(position).getChildkey()).child("playerone");
            mref.setValue(playerInfo).addOnSuccessListener(unused -> minusBlance(position));
        }

    }
    private void minusBlance(int possition) {
        DatabaseReference blancemref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
        blancemref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int firebaseBal=snapshot.getValue(Integer.class);
                int loduPrice=datalist.get(possition).getFee();
                int nowBlance=firebaseBal - loduPrice;
                uploadBlanceFirebase(nowBlance);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                c_toast(error.getMessage());
            }
        });


    }
    public void uploadBlanceFirebase(int nowBlance) {
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
        mref.setValue(nowBlance).addOnSuccessListener(unused -> {
            c_toast("successful");
        });
    }

    private void BuildRecView() {
        adapter=new loduAdapter(this,datalist);
        loduRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loduRecyclerView.setHasFixedSize(true);
        loduRecyclerView.setAdapter(adapter);

    }




    private void Two_player() {
        DatabaseReference mref= FirebaseDatabase.getInstance().getReference("lodutwoplayer");
        mref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    twoplayerData allChild=snapshot1.getValue(twoplayerData.class);
                    allChild.setChildkey(snapshot1.getKey());
                    datalist.add(allChild);
                }
                adapter.notifyDataSetChanged();
                if (datalist.size() <1){
                    norecord.setVisibility(View.VISIBLE);
                }else norecord.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void c_toast(String txt){
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }
}
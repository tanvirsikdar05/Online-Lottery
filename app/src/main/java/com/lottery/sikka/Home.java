package com.lottery.sikka;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lottery.sikka.LocalDb.DBhelper;
import com.lottery.sikka.all_adapter.lottery_adapter;
import com.lottery.sikka.all_dataclass.locaLotteryData;
import com.lottery.sikka.all_dataclass.lottery;
import com.lottery.sikka.all_dataclass.winners_image;
import com.lottery.sikka.wallets.deposite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class Home extends Fragment {
    String[] sectore={"01","02","03","4","5","6","7"};
    RecyclerView recyclerView;
    ImageView notifiacation_bar;
    CardView see_blance,spinner_wheel;
    ViewPager2 viewPager2;
    ArrayList<lottery> lotter_data=new ArrayList<>();
    Button spin_now;
    Handler sliderHandler = new Handler();
    TextView see_more,Blance;
    ArrayList<winners_image> winner_data=new ArrayList<>();
    DatabaseReference mref;
    LinearLayout two_player,four_player;
    lottery_adapter lotteryadapter;
    viewpager_adapter viewpagerAdapter;
    int bll=-1;
    String userKey;
    DBhelper dBhelper;
    Dialog prograssdialog;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view1=inflater.inflate(R.layout.home, container, false);

       prograssdialog=prograssDialogmet();

       //view pager
       init(view1);
       getUser();

        mref= FirebaseDatabase.getInstance().getReference("winner_images");
       loadLotteryData();
       build_lottery_recyclerview();
       getWinnerImages();


       Collections.reverse(Arrays.asList(sectore));






       //spin now
        spin_now.setOnClickListener(view -> startrotate());
        //add slider images
       // sliderItems.add(new slideitems(R.drawable.img3));

        //viewpager create
        build_viewpager();

        lotteryadapter.onitemclicklistener(possition -> {
            BuyLottery(possition);
        });

        //go to notification activity
        notifiacation_bar.setOnClickListener(view -> {
           startActivity(new Intent(getActivity(),notification_activity.class));
        });
        //tap to see blance
        see_blance.setOnClickListener(view -> view_blance());

        see_more.setOnClickListener(view -> startActivity(new Intent(getActivity(),more_lottery.class)));

        two_player.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(),lodu_actvity.class);
            startActivity(intent);
        });

        four_player.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(),lodu_four.class);
            startActivity(intent);
        });

        return view1;
    }
    public Dialog prograssDialogmet(){
        Dialog dialog=new Dialog(getContext());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.prograssdilog);

        return dialog;
    }

    private void getUser() {
        SharedPreferences editor = getActivity().getSharedPreferences("appname", Context.MODE_PRIVATE);
        userKey=editor.getString("loginCount","01726750916");


    }

    private void init(View view1) {
        viewPager2=view1.findViewById(R.id.viewpagerid);
        recyclerView=view1.findViewById(R.id.lottery_recyclerview);
        spinner_wheel=view1.findViewById(R.id.spinner_wheel);
        spin_now=view1.findViewById(R.id.spain_now);
        see_more=view1.findViewById(R.id.see_more);
        see_blance=view1.findViewById(R.id.see_blance);
        notifiacation_bar=view1.findViewById(R.id.imageView);
        two_player=view1.findViewById(R.id.lnyr_2player);
        four_player=view1.findViewById(R.id.lnyr_4player);
        Blance=view1.findViewById(R.id.tv_blance);
        dBhelper=new DBhelper(getActivity());
    }

    public void BuyLottery(int possition) {
        prograssdialog.show();
        Dialog lodingDialog=new Dialog(getActivity());
        lodingDialog.setContentView(R.layout.loding_dialog);
        lodingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lodingDialog.setCancelable(true);
        lodingDialog.show();

        DatabaseReference blancemref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
        blancemref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              int blance=snapshot.getValue(Integer.class);
                if (blance >= lotter_data.get(possition).getPrice()){
                    prograssdialog.dismiss();
                    LotteryConfirmDialog(possition);
                }else {
                    prograssdialog.dismiss();
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
        Dialog dialog=new Dialog(getActivity());
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
            FragmentTransaction fr = getParentFragmentManager().beginTransaction();
            fr.replace(R.id.nav_container,new deposite());
            fr.commit();
        });

        dialog.show();
    }

    public void LotteryConfirmDialog(int possition) {
        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.lotteryconfirmdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView cnfrm_btn=dialog.findViewById(R.id.tv_lotteryconfirm);
        TextView cancel=dialog.findViewById(R.id.tvcancel);

        cnfrm_btn.setOnClickListener(view -> {
            dialog.dismiss();
           prograssdialog.show();
            minusBlance(possition);

        });
        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();

    }

    public void showSuccessDialog(String LotteryNumber) {
        Dialog lodingDialog=new Dialog(getActivity());
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
                int lotteryPrice=lotter_data.get(possition).getPrice();
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

        String looteryId=lotter_data.get(possition).getId();
        String lotteryName=lotter_data.get(possition).getName() +"("+lotter_data.get(possition).getPrice()+")";
        String currentDate = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault()).format(new Date());
        String drawdatedec="Draw: "+lotter_data.get(possition).getDrawDate();

        long dbId=dBhelper.add_buying_lottery(lotteryName,userKey,currentDate,drawdatedec,"pending");
        String LotteryNumber=looteryId + userKey + dbId;

        dBhelper.update_lottery(String.valueOf(dbId),LotteryNumber);
        //upload lottery data user section
        DatabaseReference upref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("lottery").push();
        upref.setValue(new locaLotteryData(lotteryName,LotteryNumber,currentDate,
                lotter_data.get(possition).getDrawDate(),"Pending"));

        //upload lottery data global section
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("admin").child("drawlottery").child(looteryId).push();
        mref.setValue(LotteryNumber).addOnSuccessListener(unused -> {
            prograssdialog.dismiss();
            showSuccessDialog(LotteryNumber);

        });



    }

    public void loadLotteryData() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("admin").child("all_lottery");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lotter_data.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    lottery lotData=dataSnapshot.getValue(lottery.class);
                    lotter_data.add(new lottery(lotData.getId(),lotData.getDetails(),lotData.getImg(),lotData.getPrice(),
                            lotData.getName(),lotData.getDrawDate()));
                }
                lotteryadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                c_toast(error.getMessage());

            }
        });

    }

    private void getWinnerImages() {
        mref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                winner_data.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    winners_image allimage=dataSnapshot1.getValue(winners_image.class);
                    winner_data.add(new winners_image(allimage.getDoc(),allimage.getUrl()));
                }
                viewpagerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                c_toast("winner image load error");

            }
        });
    }
    public void c_toast(String txt){
        Toast.makeText(getActivity(), txt, Toast.LENGTH_SHORT).show();
    }

    public void view_blance() {

        Animation blance_refresh = AnimationUtils.loadAnimation(getContext(), R.anim.out_from_right);
        see_blance.setClickable(false);
        see_blance.startAnimation(blance_refresh);
        checkBlance();
        see_blance.setVisibility(View.GONE);
        new Handler().postDelayed(() -> {
            see_blance.setVisibility(View.VISIBLE);
            see_blance.setClickable(true);
        }, 5000);
    }

    private int getFirebaseBlance() {

        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              bll=snapshot.getValue(Integer.class);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                c_toast(error.getMessage());
            }
        });

        return bll;
    }

    public void checkBlance() {

        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("users").child(userKey).child("blance");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int _blance=snapshot.getValue(Integer.class);
                Blance.setText(String.valueOf(_blance));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                c_toast(error.getMessage());
            }
        });


    }

    private void build_viewpager() {
        //viewpager
        viewpagerAdapter=new viewpager_adapter(getContext(),viewPager2,winner_data);
        viewPager2.setAdapter(viewpagerAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                sliderHandler.removeCallbacks(sliderunable);
                sliderHandler.postDelayed(sliderunable,3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private Runnable sliderunable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() +1);
        }
    };

    private void startrotate() {
        spin_now.setClickable(false);
        Random rr=new Random();
        final int dgree=rr.nextInt(360);

        RotateAnimation rotateAnimation=new RotateAnimation(0,dgree+1080,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                calculatepoint(dgree);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        spinner_wheel.startAnimation(rotateAnimation);

        new Handler().postDelayed(() -> {
            spin_now.setClickable(true);
        }, 3000);
    }


    private void calculatepoint(int degree) {

        int intialpoin=0;
        int endpoint=51;
        int i=0;
        String res=null;
        do {
            if (degree>intialpoin && degree<endpoint){
                res=sectore[i];
            }
            intialpoin +=51;endpoint+=51;
            i++;
        }while (res==null);
        Toast.makeText(getContext(),res,Toast.LENGTH_SHORT).show();
    }
    private void build_lottery_recyclerview() {

        recyclerView.setHasFixedSize(false);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext()
        ,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        lotteryadapter=new lottery_adapter(getContext(),lotter_data);
        recyclerView.setAdapter(lotteryadapter);
        

    }
}
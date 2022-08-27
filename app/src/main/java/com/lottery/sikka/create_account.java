package com.lottery.sikka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class create_account extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView send,timer,sendtwo,login,txtviewfour;
    EditText edt,smsCode;
    private String mVerificationId;
    String user,number;
    String acountStatus="new";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        getSupportActionBar().hide();
        send=findViewById(R.id.tvnxt);
        edt=findViewById(R.id.edt_phoneNumber);
        sendtwo=findViewById(R.id.tvnexttwo);
        smsCode=findViewById(R.id.editText);
        txtviewfour=findViewById(R.id.textView4);
        login=findViewById(R.id.tvlogin);
        timer=findViewById(R.id.tvtimer);
        mAuth=FirebaseAuth.getInstance();





        send.setOnClickListener(view -> {

            if (!edt.getText().toString().isEmpty() && edt.getText().toString().length() == 11){
                user=edt.getText().toString();
                number="+88"+edt.getText().toString();
                if (acountStatus.equals("old")){
                    sendotp(number);

                }else {

                    checkUser(number);
                }
            }else {
                ctoast("Inter a valid number");
            }

        });
        login.setOnClickListener(view -> {
            if (acountStatus.equals("new")){
                login.setText("sign up");
                txtviewfour.setText("No account");
                acountStatus="old";
            }else if (acountStatus.equals("old")){
                login.setText("log in");
                txtviewfour.setText("already have account");
                acountStatus="new";
            }
        });

        sendtwo.setOnClickListener(view -> {
            if (!smsCode.getText().toString().isEmpty()){
                verifyVerificationCode(smsCode.getText().toString());
            }else {
                ctoast("cann't empty");
            }
        });

        timer.setOnClickListener(view -> {
            if(timer.getText().toString().equals("Re-send Code") && number != null){
                sendotp(number);
            }
        });
    }



    private void ctoast(String txt){
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }
    private void sendotp(String phoneNumber){
        send.setVisibility(View.GONE);
        sendtwo.setVisibility(View.VISIBLE);
        showTimer();
        edt.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        txtviewfour.setVisibility(View.GONE);
        timer.setVisibility(View.VISIBLE);
        smsCode.setVisibility(View.VISIBLE);

        if (acountStatus.equals("old")){
            DatabaseReference mref= FirebaseDatabase.getInstance().getReference("users").child(user);
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild("blance")){
                        code(phoneNumber);
                    }else {
                        Intent intent=new Intent(create_account.this,create_account.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        ctoast("No Account Found");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }else {
            code(phoneNumber);
        }
    }
    private void checkUser(String phoneNumber){
        DatabaseReference mref= FirebaseDatabase.getInstance().getReference("users").child(user);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("blance")){
                    ctoast("Already have a account please Log in");
                }else {
                    sendotp(phoneNumber);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void code(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void showTimer() {
        long duration=TimeUnit.MINUTES.toMillis(1);
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration=String.format(Locale.ENGLISH,"%02d : %02d",TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(l)
                        ));
                timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                timer.setText("Re-send Code");

                //here go to previous activity using startactivity intent

            }
        }.start();
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                smsCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
           ctoast(e.getMessage());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;

        }
    };
    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(create_account.this, task -> {
                    if (task.isSuccessful()) {
                        //verification successful we will start the profile activity
                        SharedPreferences.Editor editor = getSharedPreferences("appname", MODE_PRIVATE).edit();
                        editor.putString("loginCount", user);
                        editor.apply();


                        Intent intent = new Intent(create_account.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        if (acountStatus.equals("new")) {
                            intent.putExtra("firstLogin",1);
                        }
                        startActivity(intent);

                    } else {

                        //verification unsuccessful.. display an error message

                        String message = "Somthing is wrong, we will fix it soon...";

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered...";
                        }

                        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                        snackbar.setAction("Dismiss", v -> {
                            snackbar.dismiss();

                        });
                        snackbar.show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
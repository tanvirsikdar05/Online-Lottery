package com.lottery.sikka;

import android.app.Notification;
import android.app.Service;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.lottery.sikka.LocalDb.DBhelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    DBhelper dBhelper;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        getFirebaseMessage(message.getNotification().getTitle(),
                message.getNotification().getBody());

    }
    public void getFirebaseMessage(String title,String details){
        dBhelper=new DBhelper(getApplicationContext());
        String currentDate = new SimpleDateFormat("dd-MMM", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String dateTime=currentDate+" "+currentTime;

        if (title != null && details != null){
            dBhelper.addnotificationdata(title,details,dateTime);
        }




        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"mychannel")
                .setSmallIcon(R.id.right_icon)
                .setContentTitle(title)
                .setContentText(details)
                .setAutoCancel(true);

        NotificationManagerCompat manager=NotificationManagerCompat.from(this);
        manager.notify(101,builder.build());

    }
}

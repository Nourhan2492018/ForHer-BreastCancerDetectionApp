package com.example.forherapp.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.forherapp.R;

public class MyReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
       NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify")
            .setSmallIcon(R.drawable.breast1)
            .setContentTitle("For you")
            .setContentText("Once I overcame breast cancer, I was not afraid of anything anymore.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

      /*  NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context, "notify1")
                .setSmallIcon(R.drawable.breast1)
                .setContentTitle("For her")
                .setContentText("you are so strong keep going")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/

    notificationManagerCompat.notify(1,builder.build());



}

}
package com.example.aasystem;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.aasystem.company.activities.MapsActivity;

public class notyLeave extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("001", "Auto", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Desc");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificn = new NotificationCompat.Builder(context, "001")
                .setContentTitle("Auto Attendance")
                .setContentText("Confirm Your Leave")
                .setSmallIcon(R.drawable.logo_invert)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo2))
                .setAutoCancel(true)
                .setNumber(1);

        Intent i = new Intent(context.getApplicationContext(), MapsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 0 , i, PendingIntent.FLAG_UPDATE_CURRENT);
        notificn.setContentIntent(pi);
        notificn.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        notificn.setVibrate(new long[]{500,1000,500,1000,500});

        notificationManager.notify(1, notificn.build());

    }

}

package com.app.libandroidapp.Plugin;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.app.libandroidapp.R;

public class Alerts {

    public  static  void showDilog(Context context, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.alerts_dialog_title));
        builder.setMessage(message);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setNegativeButton(R.string.ok_btn, null);
        builder.show();

    }


    public static  void  localNotification(Context context, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.alerts_dialog_title))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(0,  builder.build());



    }
}

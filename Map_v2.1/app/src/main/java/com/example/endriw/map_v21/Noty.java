package com.example.endriw.map_v21;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Endriw on 30/10/2016.
 */
public class Noty extends BroadcastReceiver{

    public final static String ACTION = "StartSmartDog";

    @Override
    public void onReceive(android.content.Context context, Intent intent) {

        Intent intent_service = new Intent(context, smartDog.class);
        context.startService(intent_service);

    }

    public static class NotificationUtil{

        private static final String TAG = "android";

        public static void create(Context context, int id, Intent intent, String contentTitle, String contentText){
            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            PendingIntent p = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //criar notificação

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentIntent(p)                //evento clique
                    .setContentTitle(contentTitle)      //titulo
                    .setContentText(contentText)        //texto
                    .setSmallIcon(R.mipmap.ic_launcher)  //icone
                    .setAutoCancel(true)                 //remove a notificação
                    .setVibrate(new long[]{ 1000, 1000, 1000, 1000, 1000});   //vibração


            //disparar a notificação
            Notification n = builder.build();

            manager.notify(id, n);
        }
    }
}

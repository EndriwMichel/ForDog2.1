package com.example.endriw.map_v21;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * Created by Endriw on 30/10/2016.
 */
public class Alarm {

    private static final String TAG = "android book";

    //agendar alarme
    public static void schedule(Context context, Intent intent, long triggerAtMillis) {
        PendingIntent p = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); //servço do proprio celular
        alarme.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, p);
        Log.d(TAG, "Alarme agendado");

    }
    //repetir alarme

    public static void scheduleRepeat(Context context, Intent intent, long triggerAtMillis, long intervalMillis) {
        PendingIntent p = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); //servço do proprio celular
        alarme.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, p);
        Log.d(TAG, "Alarme agendado com repetição");
    }
    //cancelar alarme
    public static void cancel(Context context, Intent intent){
        AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); //servço do proprio celular
        PendingIntent p = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarme.cancel(p);
        Log.d(TAG, "Alarme cancelado");
    }
}

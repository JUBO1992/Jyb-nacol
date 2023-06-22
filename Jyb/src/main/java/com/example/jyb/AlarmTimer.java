package com.example.jyb;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmTimer {

    public static void cancelAlarmTimer(Context paramContext, String paramString)
    {
        Intent localIntent = new Intent();
        localIntent.setAction(paramString);
        PendingIntent broadcast = PendingIntent.getBroadcast(paramContext, 0, localIntent, 0);
        ((AlarmManager)paramContext.getSystemService(Context.ALARM_SERVICE)).cancel(broadcast);
    }

    public static void setAlarmTimer(Context paramContext, long paramLong, String paramString1, int paramInt, String paramString2)
    {
        Intent localIntent = new Intent();
        localIntent.putExtra("date", paramString2);
        localIntent.setAction(paramString1);
        int i = SharedPreUtils.getInt(paramContext, "alarm_id", 0) + 1;
        SharedPreUtils.saveInt(paramContext, "alarm_id", i);
        PendingIntent broadcast = PendingIntent.getBroadcast(paramContext, i, localIntent, 0);
        ((AlarmManager)paramContext.getSystemService(Context.ALARM_SERVICE)).set(paramInt, paramLong, broadcast);
    }

    public static void setRepeatingAlarmTimer1(Context paramContext, long paramLong1, long paramLong2, String paramString, int paramInt)
    {
        Intent localIntent = new Intent(paramContext, AlarmReceiver.class);
        localIntent.setAction(paramString);
        PendingIntent broadcast = PendingIntent.getBroadcast(paramContext, 19910817, localIntent, 0);
        ((AlarmManager)paramContext.getSystemService(Context.ALARM_SERVICE)).setRepeating(paramInt, paramLong1, paramLong2, broadcast);
    }

    public static void setRepeatingAlarmTimer2(Context paramContext, long paramLong1, long paramLong2, String paramString, int paramInt)
    {
        Intent localIntent = new Intent(paramContext, AlarmReceiver.class);
        localIntent.setAction(paramString);
        PendingIntent broadcast = PendingIntent.getBroadcast(paramContext, 19920613, localIntent, 0);
        ((AlarmManager)paramContext.getSystemService(Context.ALARM_SERVICE)).setRepeating(paramInt, paramLong1, paramLong2, broadcast);
    }
}

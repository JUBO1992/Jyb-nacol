package com.example.jyb;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_FLAG1 = 19910817;
    private static final int NOTIFICATION_FLAG2 = 19920613;
    private NotificationManager m_notificationMgr = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        List<String> msgList = new LinkedList<>();
        msgList.add("记得多喝水哦");
        msgList.add("坐久了要起来活动活动哟");
        msgList.add("坐累了可以伸个懒腰");
        msgList.add("别对着电脑太久了");
        msgList.add("༄　❧　⚘　✎　✑　✂　✄　◙　❦　❈　❊　✻");
        String msg = msgList.get((int)(Math.random() * msgList.size()));
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        int i = instance.get(Calendar.HOUR_OF_DAY);
        int j = instance.get(Calendar.MINUTE);
        if ((i==7) || ((i==8) && (j <= 30))) {
            msg = "~别忘了吃早餐呦！~";
        } else if ((i == 22) && (j >= 30) || (i == 23)) {
            msg = "~别玩手机了，该睡觉了！~";
        }
        m_notificationMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getAction().equals("com.example.jyb.TIMER_ACTION")) {
            Log.i("alarm_receiver", "com.example.jyb.TIMER_ACTION");
        } else if (intent.getAction().equals("com.example.jyb.TIMER_ACTION_REPEATING1")) {
            Log.i("alarm_receiver", "周期闹钟");
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_ice);
            PendingIntent activity = PendingIntent.getActivity(
                    context,
                    NOTIFICATION_FLAG1,
                    new Intent(context, FullscreenActivity.class),
                    0);
            Notification concernFromJubo = new Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher_ice)
                    .setLargeIcon(bitmap)
                    .setTicker("Concern from JUBO")
                    .setContentTitle(" <◔ܫ◔> ")
                    .setContentText(msg)
                    .setContentIntent(activity)
                    .setNumber(1).build();
            concernFromJubo.flags = 1;
            m_notificationMgr.notify(NOTIFICATION_FLAG1, concernFromJubo);
            bitmap.recycle();
        } else if (intent.getAction().equals("com.example.jyb.TIMER_ACTION_REPEATING2")) {
            Log.i("alarm_receiver", "com.example.jyb.TIMER_ACTION_REPEATING2");
        }
    }
}

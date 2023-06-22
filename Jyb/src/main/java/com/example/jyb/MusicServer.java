package com.example.jyb;

import android.app.Service;  
import android.content.Intent;  
import android.media.MediaPlayer;  
import android.os.IBinder;

import java.util.Calendar;

public class MusicServer extends Service {  
  
    private MediaPlayer mediaPlayer;  
  
    @Override  
    public IBinder onBind(Intent intent) {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    @Override  
    public void onStart(Intent intent, int startId) {  
        super.onStart(intent, startId);
  
        if (mediaPlayer == null) {
            Calendar calendar = Calendar.getInstance();
            int m = calendar.get(Calendar.MONTH) + 1;
            int d = calendar.get(Calendar.DAY_OF_MONTH);
            String lm = DateUtils.getLunarMonth();
            String ld = DateUtils.getLunarDay();
            if((m==8 && d==17) || ((lm.equals("七月")) && (ld.equals("初八")))) {
                // R.raw.***是资源文件，MP3格式的
                mediaPlayer = MediaPlayer.create(MusicServer.this, R.raw.happybirthday);
            } else {
                // R.raw.***是资源文件，MP3格式的
                mediaPlayer = MediaPlayer.create(MusicServer.this, R.raw.music);
            }
            mediaPlayer.setLooping(true);
            mediaPlayer.start();  
        }  
    }  
  
    @Override  
    public void onDestroy() {  
        // TODO Auto-generated method stub  
        super.onDestroy();  
        mediaPlayer.stop();
        mediaPlayer.release();
    }  
}  
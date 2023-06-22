package com.example.jyb;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends Activity {
	private int interval = 3000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		findViewById(R.id.skipWelcome_btn).setOnClickListener(btnClickListener);

        InitWelcomePage();
        
        handler = new Handler();
        // 延迟跳转到MainActivity
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
				startActivity(new Intent(WelcomeActivity.this, FullscreenActivity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
                WelcomeActivity.this.finish();
            }
        }, interval);
    }
    
    @Override    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
    		return  true;
    	} 
    	return  super.onKeyDown(keyCode, event);     
    }
    
	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.skipWelcome_btn:
				startActivity(new Intent(WelcomeActivity.this, FullscreenActivity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
                WelcomeActivity.this.finish();
				break;
			default:
				break;
			}
		}
	};
    
    private void InitWelcomePage() {
    	ImageView imgView = (ImageView)findViewById(R.id.welcomeImgV);
    	TextView solarTextV = (TextView)findViewById(R.id.solarTextV);
    	TextView lunarTextV = (TextView)findViewById(R.id.lunarTextV);
		AssetManager assets = getAssets();
		Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/consola.ttf");
		solarTextV.setTypeface(fromAsset);
		lunarTextV.setTypeface(fromAsset);
    	
    	Calendar calendar = Calendar.getInstance();
    	int m = calendar.get(Calendar.MONTH) + 1;
    	int d = calendar.get(Calendar.DAY_OF_MONTH);
    	String lm = DateUtils.getLunarMonth();
    	String ld = DateUtils.getLunarDay();
    	String week = DateUtils.getWeek();
    	
    	findViewById(R.id.solarTextV).setVisibility(View.INVISIBLE);
    	findViewById(R.id.lunarTextV).setVisibility(View.INVISIBLE);
    	findViewById(R.id.skipWelcome_btn).setVisibility(View.VISIBLE);
//    	solarTextV.setText(m+"月"+d+"日");
    	solarTextV.setText(DateUtils.getDate());
    	lunarTextV.setText(lm+ld);
    	
    	interval = 5000;
		if(m==2 && d==14) {
			imgView.setBackgroundResource(R.drawable.welcome01);
		}else if(lm.equals("七月") && ld.equals("初七")) {
			imgView.setBackgroundResource(R.drawable.welcome01);
		}else if(m==8 && d==17) {
    		imgView.setBackgroundResource(R.drawable.welcome02);
		}else if(lm.equals("七月") && ld.equals("初八")) {
			imgView.setBackgroundResource(R.drawable.welcome02);
    	}else if(lm.equals("腊月") && ld.equals("三十")) {
    		imgView.setBackgroundResource(R.drawable.welcome03);
    	}else if(lm.equals("正月") && ld.equals("初一")) {
    		imgView.setBackgroundResource(R.drawable.welcome04);
    	}else if(lm.equals("正月") && ld.equals("十五")) {
    		imgView.setBackgroundResource(R.drawable.welcome05);
    	}else if(m==12 && d==25) {
    		imgView.setBackgroundResource(R.drawable.welcome06);
    	}else { 
    		findViewById(R.id.solarTextV).setVisibility(View.VISIBLE);
    		findViewById(R.id.lunarTextV).setVisibility(View.VISIBLE);
    		findViewById(R.id.skipWelcome_btn).setVisibility(View.VISIBLE);
    		interval = 817 * 6;
    		if(week.equals("星期一")) {
    			imgView.setBackgroundResource(R.drawable.welcome14);
    		}else if(week.equals("星期二")) {
    			imgView.setBackgroundResource(R.drawable.welcome15);
    		}else if(week.equals("星期三")) {
    			imgView.setBackgroundResource(R.drawable.welcome16);
    		}else if(week.equals("星期四")) {
    			imgView.setBackgroundResource(R.drawable.welcome17);
    		}else if(week.equals("星期五")) {
    			imgView.setBackgroundResource(R.drawable.welcome18);
    		}else if(week.equals("星期六")) {
    			imgView.setBackgroundResource(R.drawable.welcome19);
    		}else if(week.equals("星期日")) {
    			imgView.setBackgroundResource(R.drawable.welcome20);
    		}else {
        		imgView.setImageResource(R.drawable.welcome_1);
    		}
    	}
    }
}

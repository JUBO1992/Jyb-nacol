package com.example.jyb;

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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WelcomeActivity extends Activity {
	private int interval = 3000;
    private Handler handler;
	private boolean isSkipped = false;

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
				if (!isSkipped) {
					startActivity(new Intent(WelcomeActivity.this, FullscreenActivity.class));
					overridePendingTransition(R.anim.ap2, R.anim.ap1);
					WelcomeActivity.this.finish();
				}
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
				isSkipped = true;
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

		TextView gestationWeek = (TextView)findViewById(R.id.gestationWeek);
		gestationWeek.setTypeface(fromAsset);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDay = null;
		try {
			firstDay = formatter.parse("2023-04-12");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		Date today = new Date();
		int diff = differentDays(firstDay, today);
		if (diff > 300 || Calendar.getInstance().get(Calendar.YEAR) > 2024) {
			findViewById(R.id.gestationWeek).setVisibility(View.INVISIBLE);
		} else {
			int weekNum = diff / 7;
			int dayNum = diff % 7;
			int countDown = 280 - diff;
			DecimalFormat df1 = new DecimalFormat("00");
			DecimalFormat df2 = new DecimalFormat("000");
			gestationWeek.setText(df1.format(weekNum) + "周" + df1.format(dayNum) + "天\n"
					+ "距离预产期" + df2.format(countDown) + "天");
		}

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

	/**
	 * date2比date1多的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	private static int differentDays(Date date1,Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2) {//不同年
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		} else {//同一年
			System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2-day1;
		}
	}
}

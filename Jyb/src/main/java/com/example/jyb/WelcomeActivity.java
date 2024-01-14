package com.example.jyb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.AgeCalculator;
import com.example.util.GestationWeek;
import com.example.util.ImageUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

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
    
	@SuppressLint("SetTextI18n")
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
				if (Math.random() > 0.5) {
					imgView.setBackgroundResource(R.drawable.welcome14);
				} else {
					imgView.setBackgroundResource(R.drawable.baby01_photo01);
				}
    		}else if(week.equals("星期二")) {
				if (Math.random() > 0.5) {
					imgView.setBackgroundResource(R.drawable.welcome15);
				} else {
					imgView.setBackgroundResource(R.drawable.baby01_photo02);
				}
    		}else if(week.equals("星期三")) {
				if (Math.random() > 0.5) {
					imgView.setBackgroundResource(R.drawable.welcome16);
				} else {
					imgView.setBackgroundResource(R.drawable.baby01_photo03);
				}
    		}else if(week.equals("星期四")) {
				if (Math.random() > 0.5) {
					imgView.setBackgroundResource(R.drawable.welcome17);
				} else {
					imgView.setBackgroundResource(R.drawable.baby01_photo04);
				}
    		}else if(week.equals("星期五")) {
				if (Math.random() > 0.5) {
					imgView.setBackgroundResource(R.drawable.welcome18);
				} else {
					imgView.setBackgroundResource(R.drawable.baby01_photo05);
				}
    		}else if(week.equals("星期六")) {
    			// imgView.setBackgroundResource(R.drawable.welcome19);
				imgView.setBackgroundResource(R.drawable.baby01_photo06);
    		}else if(week.equals("星期日")) {
    			// imgView.setBackgroundResource(R.drawable.welcome20);
				imgView.setBackgroundResource(R.drawable.baby01_photo07);
			}else {
        		imgView.setImageResource(R.drawable.welcome_1);
    		}
    	}

		Date today = new Date();
		DecimalFormat df1 = new DecimalFormat("00");
		DecimalFormat df2 = new DecimalFormat("000");

		TextView gestationWeek = (TextView)findViewById(R.id.gestationWeek);
		gestationWeek.setTypeface(fromAsset);
		@SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDay = null;
		try {
			firstDay = formatter.parse("2023-04-12");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		GestationWeek gesWeekUtil = new GestationWeek(firstDay, today);
		int countDay = gesWeekUtil.countDay();
		if (countDay > 300) {
			findViewById(R.id.gestationWeek).setVisibility(View.INVISIBLE);
		} else {
			gestationWeek.setText(df1.format(gesWeekUtil.weekNum()) + "周" + df1.format(gesWeekUtil.dayNum()) + "天\n"
					+ "距离预产期" + df2.format(gesWeekUtil.countDown()) + "天");
		}

		TextView baby01Age = (TextView)findViewById(R.id.baby01Age);
		baby01Age.setTypeface(fromAsset);
		Date birthDay = null;
		try {
			birthDay = formatter.parse("2024-01-07");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		AgeCalculator ageCalcUtil = new AgeCalculator(birthDay, today);
		baby01Age.setText("今天我" + df1.format(ageCalcUtil.year()) + "岁" + df2.format(ageCalcUtil.dayOfYear()) + "天啦~\n"
				+ (true ? "" : "这是出生第8天的我"));
	}

}

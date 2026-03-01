package com.example.jyb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.AgeCalculator;
import com.example.util.GestationWeek;

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

		// 隐藏倒计时功能
		findViewById(R.id.gestationWeek).setVisibility(View.INVISIBLE);

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
    		// 随机切换展示两个女儿的照片
    		if(Math.random() > 0.5) {
    			// 展示大女儿照片
    			if(week.equals("星期一")) {
    				imgView.setBackgroundResource(R.drawable.baby01_photo01);
    			}else if(week.equals("星期二")) {
    				imgView.setBackgroundResource(R.drawable.baby01_photo02);
    			}else if(week.equals("星期三")) {
    				imgView.setBackgroundResource(R.drawable.baby01_photo03);
    			}else if(week.equals("星期四")) {
    				imgView.setBackgroundResource(R.drawable.baby01_photo04);
    			}else if(week.equals("星期五")) {
    				imgView.setBackgroundResource(R.drawable.baby01_photo05);
    			}else if(week.equals("星期六")) {
    				imgView.setBackgroundResource(R.drawable.baby01_photo06);
    			}else if(week.equals("星期日")) {
    				imgView.setBackgroundResource(R.drawable.baby01_photo07);
    			}else {
    				imgView.setBackgroundResource(R.drawable.welcome_1);
    			}
    		} else {
    			// 展示二女儿照片
    			if(week.equals("星期一")) {
    				imgView.setBackgroundResource(R.drawable.baby02_photo01);
    			}else if(week.equals("星期二")) {
    				imgView.setBackgroundResource(R.drawable.baby02_photo02);
    			}else if(week.equals("星期三")) {
    				imgView.setBackgroundResource(R.drawable.baby02_photo03);
    			}else if(week.equals("星期四")) {
    				imgView.setBackgroundResource(R.drawable.baby02_photo04);
    			}else if(week.equals("星期五")) {
    				imgView.setBackgroundResource(R.drawable.baby02_photo05);
    			}else if(week.equals("星期六")) {
    				imgView.setBackgroundResource(R.drawable.baby02_photo06);
    			}else if(week.equals("星期日")) {
    				imgView.setBackgroundResource(R.drawable.baby02_photo07);
    			}else {
    				imgView.setBackgroundResource(R.drawable.welcome_1);
    			}
    		}
    	}

		Date today = new Date();
		DecimalFormat df1 = new DecimalFormat("00");
		DecimalFormat df2 = new DecimalFormat("000");

		// 显示大女儿年龄
		TextView baby01Age = (TextView)findViewById(R.id.baby01Age);
		baby01Age.setTypeface(fromAsset);
		Date birthDay01 = null;
		try {
			birthDay01 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-07");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		AgeCalculator ageCalcUtil01 = new AgeCalculator(birthDay01, today);
		baby01Age.setText("姐姐今天" + df1.format(ageCalcUtil01.year()) + "岁" + df2.format(ageCalcUtil01.dayOfYear()) + "天啦~\n" +
				"(" + df1.format(ageCalcUtil01.year()) + "岁" + df1.format(ageCalcUtil01.month()) + "月" + df1.format(ageCalcUtil01.day()) + "天)");

		// 显示二女儿年龄
		TextView baby02Age = (TextView)findViewById(R.id.baby02Age);
		if(baby02Age != null) {
			baby02Age.setTypeface(fromAsset);
			Date birthDay02 = null;
			try {
				birthDay02 = new SimpleDateFormat("yyyy-MM-dd").parse("2026-02-13");
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			AgeCalculator ageCalcUtil02 = new AgeCalculator(birthDay02, today);
			baby02Age.setText("妹妹今天" + df1.format(ageCalcUtil02.year()) + "岁" + df2.format(ageCalcUtil02.dayOfYear()) + "天啦~\n" +
					"(" + df1.format(ageCalcUtil02.year()) + "岁" + df1.format(ageCalcUtil02.month()) + "月" + df1.format(ageCalcUtil02.day()) + "天)");
		}
	}

}

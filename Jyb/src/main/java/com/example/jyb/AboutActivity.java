package com.example.jyb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.AgeCalculator;
import com.example.util.GestationWeek;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AboutActivity extends Activity {
	// 记录上次显示的是姐姐还是妹妹的照片，true表示姐姐，false表示妹妹
	private static boolean lastShowSister = true;

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		findViewById(R.id.close_btn).setOnClickListener(btnClickListener);

		AssetManager assets = getAssets();
		Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/consola.ttf");
		TextView authorText = (TextView)findViewById(R.id.author_info);
		TextView versionText = (TextView)findViewById(R.id.version_info);
		authorText.setTypeface(fromAsset);
		versionText.setTypeface(fromAsset);

		// 隐藏倒计时功能
		findViewById(R.id.gestationWeek).setVisibility(View.INVISIBLE);

		ImageView imgView = (ImageView)findViewById(R.id.aboutImgV);
		String week = DateUtils.getWeek();
		// 轮流切换展示两个女儿的照片
		if(lastShowSister) {
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
			// 下次显示妹妹的照片
			lastShowSister = false;
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
			// 下次显示姐姐的照片
			lastShowSister = true;
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

	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.close_btn:
				closeActivity();
				break;
			default:
				break;
			}
		}
	};

	private void closeActivity() {
		finish();
		overridePendingTransition(R.anim.ap2, R.anim.ap1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			closeActivity();
		}
		return super.onKeyDown(keyCode, event);
	}

}

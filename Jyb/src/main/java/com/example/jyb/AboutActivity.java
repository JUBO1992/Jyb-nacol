package com.example.jyb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.util.AgeCalculator;
import com.example.util.GestationWeek;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AboutActivity extends Activity {
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
		baby01Age.setText(df1.format(ageCalcUtil.year()) + "岁 " + df2.format(ageCalcUtil.dayOfYear()) + "天");
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

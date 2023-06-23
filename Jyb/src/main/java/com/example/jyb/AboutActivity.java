package com.example.jyb;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AboutActivity extends Activity {
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

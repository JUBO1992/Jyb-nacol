package com.example.jigsawpuzzle;

import com.example.jyb.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

public class JigsawPuzzleActivity extends Activity {
	private long firstTime = 0;
	private static JigsawPuzzleActivity jigsawPuzzleActivity = null;
	private JigsawView jigsawView = null;
	private Bitmap mBitmap = null;
	private int imgIndex = 0;
	private int[] bitmapArr = { R.drawable.whale2, R.drawable.deer2, R.drawable.dolphin2, R.drawable.seafloor2,
			R.drawable.hunt2, R.drawable.hotel2 };
	private int time = 0;// 游戏时间
	private boolean countFlag = false;

	public JigsawPuzzleActivity() {
		jigsawPuzzleActivity = this;
	}

	public static JigsawPuzzleActivity getJPActivity() {
		return jigsawPuzzleActivity;
	}

	private void setThumbnail(Bitmap bitmap) {
		((ImageView) findViewById(R.id.thumbnail)).setImageBitmap(bitmap);
	}

	public void setSteps(int steps) {
		((TextView) findViewById(R.id.stepTView)).setText(steps + "");
	}

	public void resetTime() {
		time = 0;
	}

	public void startTime() {
		countFlag = true;
	}

	public void stopTime() {
		countFlag = false;
	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (countFlag) {
				time++;
				((TextView) findViewById(R.id.timeTView)).setText(time + "");
				handler.postDelayed(this, 1000);
			}
		}
	};

	private void changeBitmap(Bitmap bitmap) {
		recyclerBitmap(mBitmap);
		mBitmap = bitmap;
		if (jigsawView != null) {
			jigsawView.setBitmap(mBitmap);
		}
		setThumbnail(ImageSplitter.thumbnail(mBitmap));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jigsawpuzzle);

		jigsawView = (JigsawView) findViewById(R.id.jigsawView);
		changeBitmap(BitmapFactory.decodeResource(getResources(), bitmapArr[imgIndex]));
		findViewById(R.id.level_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.pick_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.restart_btn).setOnClickListener(btnClickListener);
		runnable.run();
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
	}

	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.level_btn:
				jigsawView.nextLevel();
				break;
			case R.id.pick_btn:
				imgIndex = (imgIndex + 1) % bitmapArr.length;
				changeBitmap(BitmapFactory.decodeResource(getResources(), bitmapArr[imgIndex]));
				jigsawView.restart();
				break;
			case R.id.restart_btn:
				jigsawView.restart();
				break;
			}
		}
	};

	private void closeActivity() {
		recyclerBitmap(mBitmap);
		System.gc();
		finish();
		overridePendingTransition(R.anim.ap2, R.anim.ap1);
	}

	private void recyclerBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {
				Toast.makeText(this, "再按一次退出页面", Toast.LENGTH_SHORT).show();
				firstTime = secondTime;
				return true;
			} else {
				closeActivity();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}

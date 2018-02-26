package com.example.jyb;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

public class AsciiArtActivity extends Activity {
	private long firstTime = 0;
	ImageView mImageView1 = null;
	TextView mTextView1 = null;
	Bitmap mZoomBitmap = null;
	Bitmap mOriBitmap = null;
	String mAsciiStr = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asciiart);

		findViewById(R.id.convert_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.openfile_btn).setOnClickListener(btnClickListener);

		mImageView1 = (ImageView) findViewById(R.id.imageView1);
		mTextView1 = (TextView) findViewById(R.id.textView1);
		AssetManager assets = getAssets();
		Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/consola.ttf");
		mTextView1.setTypeface(fromAsset);
		String str = "", temp = "";
		for (int i = 0; i < 140; i++) {
			temp += ".";
		}
		for (int j = 0; j < 60; j++) {
			str += temp + "\n";
		}
		mTextView1.setText(str);
	}

	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.convert_btn:
				if (mZoomBitmap != null) {
					createAsciiPic(mZoomBitmap);
				}
				break;
			case R.id.openfile_btn:
				getPhonePhoto();
				break;
			default:
				break;
			}
		}
	};

	private void closeActivity() {
		recyclerBitmap(mZoomBitmap);
		recyclerBitmap(mOriBitmap);
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

	// 启动图片选择Activity
	public void getPhonePhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				Uri uri = data.getData();
				try {
					recyclerBitmap(mZoomBitmap);
					recyclerBitmap(mOriBitmap);
					System.gc();
					InputStream inputStream = getContentResolver().openInputStream(uri);
					mOriBitmap = BitmapFactory.decodeStream(inputStream);
					int dstWidth = 0, dstHeight = 0, maxcharnum = 140;
					if (mOriBitmap.getWidth() > mOriBitmap.getHeight()) {
						dstWidth = maxcharnum;
						dstHeight = (int) ((1.0 * mOriBitmap.getHeight() / mOriBitmap.getWidth()) * maxcharnum);
					} else {
						dstHeight = maxcharnum;
						dstWidth = (int) ((1.0 * mOriBitmap.getWidth() / mOriBitmap.getHeight()) * maxcharnum);
					}
					mZoomBitmap = Bitmap.createScaledBitmap(mOriBitmap, dstWidth, dstHeight, false);
					setImageViewSize(mOriBitmap);
					mImageView1.setImageBitmap(mOriBitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	private void setImageViewSize(Bitmap bitmap) {
		Display display = getWindowManager().getDefaultDisplay();
		int height = bitmap.getHeight(), width = display.getWidth();
		if (height >= width) {
			if (height >= bitmap.getWidth()) {
				height = width;
			} else {
				height = (int) (1.0 * bitmap.getHeight() / bitmap.getWidth() * width);
			}
		}
		LayoutParams params = mImageView1.getLayoutParams();
		params.height = height > 1080 ? height : 1080;
		mImageView1.setLayoutParams(params);
	}

	private void createAsciiPic(Bitmap bitmap) {
		mAsciiStr = "\n";
		String base = "@#&$%*o!;.";// 字符串由复杂到简单
		try {
			for (int y = 0; y < bitmap.getHeight(); y += 2) {
				mAsciiStr += "  ";
				for (int x = 0; x < bitmap.getWidth(); x++) {
					int pixel = bitmap.getPixel(x, y);
					int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
					float gray = 0.299f * r + 0.578f * g + 0.114f * b;
					int index = Math.round(gray * (base.length() + 1) / 255);
					mAsciiStr += index >= base.length() ? " " : String.valueOf(base.charAt(index));
				}
				mAsciiStr += "  \n";
			}
			mTextView1.setText(mAsciiStr);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}
